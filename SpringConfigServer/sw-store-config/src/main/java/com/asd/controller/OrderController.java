package com.asd.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asd.DTO.CanceledOrderDto;
import com.asd.DTO.OrderDetailDto;
import com.asd.DTO.OrderDto;
import com.asd.common.OrderStatus;
import com.asd.model.Address;
import com.asd.model.OrderTable;
import com.asd.model.OrderDetail;
import com.asd.model.Product;
import com.asd.model.User;
import com.asd.service.AddressService;
import com.asd.service.OrderService;
import com.asd.service.ProductService;
import com.asd.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {
	private final OrderService orderService;
	private final UserService userService;
	private final ProductService productService;
	private final AddressService addressService;
	private Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	//주문 등록 + 주문 내역 상세 등록
	@PostMapping("/add")
	public Map<String, String> addOrder(HttpServletRequest request, @RequestParam String product_id,
			@RequestParam String cnt) {
		User user = userService.findUser(request); //유저 정보 추출
		Product product = productService.getProduct(Long.parseLong(product_id)); //상품 정보 추출
		Address address = addressService.getAddress(user); //배송지 정보 추출
		
		OrderTable order = new OrderTable();
		order.setUser(user);
		order.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(Integer.parseInt(cnt)))); //상품 가격에 개수를 곱함
		order.setStatus(OrderStatus.PENDING);
		order.setRemove(false);
		
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setProduct(product);
		orderDetail.setPrice(product.getPrice());
		orderDetail.setCnt(Integer.parseInt(cnt));
		orderDetail.setName(address.getName());
		orderDetail.setAddr(address.getAddr());
		orderDetail.setPhonenumber(address.getPhonenumber());
		orderDetail.setReq(address.getReq());
				
		orderDetail.setOrderTable(order);
		order.setOrderDetail(orderDetail);
		
		orderService.addOrder(order);
		Map<String, String> response = new HashMap<>();
		response.put("check", "true");
		logger.info("[addOrder] {} 사용자가 {} 상품을 {}개 주문했습니다.", user.getId(), product_id, cnt);
    	return response;
	}
	
	//주문 취소(환불 개념)
	@PostMapping("/cancel")
	public Map<String, String> cancelOrder(@RequestParam String order_id, @RequestParam String reason) {
		orderService.cancelOrder(Long.parseLong(order_id), reason);
		Map<String, String> response = new HashMap<>();
		response.put("check", "true");
		logger.info("[cancelOrder] {} 주문이 취소되었습니다.", order_id);
    	return response;
	}
		
	//주문 내역 삭제
	@DeleteMapping("/delete") //유저의 입장에서 삭제, 실제 DB에는 주문 데이터가 남아있음
	public Map<String, String> deleteOrder(@RequestParam String order_id) {
		orderService.deleteOrder(Long.parseLong(order_id));
		Map<String, String> response = new HashMap<>();
		response.put("check", "true");
    	return response;
	}
	
	//주문 내역 전체 리스트 출력
	@GetMapping("/list")
	public List<OrderDto> getOrderList(HttpServletRequest request) {
		User user = userService.findUser(request); //유저 정보 추출
		return orderService.orderList(user);
	}
	
	//주문 내역 상세 출력
	@GetMapping("/view")
	public OrderDetailDto viewOrderDetail(@RequestParam String order_id) {
		return orderService.viewOrderDetail(Long.parseLong(order_id));
	}
	
	//취소 내역 전체 리스트 출력
	@GetMapping("/list/cencel")
	public List<OrderDto> getCencelList(HttpServletRequest request) {
		User user = userService.findUser(request); //유저 정보 추출
		return orderService.cencelList(user);
	}
	
	//취소 내역 상세 출력
	@GetMapping("/view/cencel")
	public CanceledOrderDto viewCencelDetail(@RequestParam String order_id) {
		return orderService.viewCencelDetail(Long.parseLong(order_id));
	}
}

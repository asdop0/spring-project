package com.asd.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asd.DTO.CanceledOrderDto;
import com.asd.DTO.OrderDetailDto;
import com.asd.DTO.OrderDto;
import com.asd.DTO.UserOrderDto;
import com.asd.common.OrderCancelStatus;
import com.asd.common.OrderStatus;
import com.asd.model.CanceledOrder;
import com.asd.model.OrderDetail;
import com.asd.model.OrderTable;
import com.asd.model.Product;
import com.asd.model.User;
import com.asd.repository.CanceledOrderRepository;
import com.asd.repository.OrderTableRepository;
import com.asd.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderService {
	private final OrderTableRepository orderRepository;
	private final ProductRepository productRepository;
	private final CanceledOrderRepository canceledOrderRepository;
	
	//전달받은 주문 저장
	@Transactional
	public void addOrder(OrderTable order) {
		Product product = order.getOrderDetail().getProduct();
		product.setTotalSales(product.getTotalSales() + 1);
		
		productRepository.save(product);
		orderRepository.save(order);
	}
	
	//주문 취소
	@Transactional
	public void cancelOrder(Long id, String reason) {
		OrderTable order = orderRepository.findById(id).orElseThrow(() -> 
        	new IllegalArgumentException("[cancelOrder] 주문을 찾을 수 없습니다.")
		);
		order.setStatus(OrderStatus.CANCELLED);		
		orderRepository.save(order);	
		
		Product product = order.getOrderDetail().getProduct();
		product.setTotalSales(product.getTotalSales() - 1);
		productRepository.save(product);
		
		CanceledOrder canceledOrder = new CanceledOrder();
		canceledOrder.setUser(order.getUser());
		canceledOrder.setProduct(order.getOrderDetail().getProduct());
		canceledOrder.setReason(reason);
		canceledOrder.setStatus(OrderCancelStatus.REQUESTED);
		canceledOrder.setTotalPrice(order.getTotalPrice());
		canceledOrder.setOrderTable(order);
		canceledOrderRepository.save(canceledOrder);
	}
	
	//주문 삭제
	@Transactional
	public void deleteOrder(Long id) {
		OrderTable order = orderRepository.findById(id).orElseThrow(() -> 
        	new IllegalArgumentException("[deleteOrder] 주문을 찾을 수 없습니다.")
		);
		order.setRemove(true);
		
		orderRepository.save(order);		
	}
	
	//해당 유저의 주문 내역 조회
	public List<OrderDto> orderList(User user) {
		List<OrderTable> orders = orderRepository.findAllByUserAndRemoveFalse(user);
		List<OrderDto> orderDtos = new ArrayList<>();
		for(OrderTable order : orders) {
            orderDtos.add(OrderDto.toDto(order));
        }
        return orderDtos;
	}
	
	//지정된 주문의 상세정보를 출력
	public OrderDetailDto viewOrderDetail(Long id) {
		OrderTable order = orderRepository.findById(id).orElseThrow(() -> 
    		new IllegalArgumentException("[viewOrderDetail] 주문을 찾을 수 없습니다.")
		);
		OrderDetail orderDetail = order.getOrderDetail();
			
		return OrderDetailDto.toDto(orderDetail);
	}
	
	//해당 유저의 취소 내역 조회
	public List<OrderDto> cencelList(User user) {
		List<OrderTable> orders = orderRepository.findAllByUserAndRemoveFalseAndStatus(user, OrderStatus.CANCELLED);
		List<OrderDto> orderDtos = new ArrayList<>();
		for(OrderTable order : orders) {
            orderDtos.add(OrderDto.toDto(order));
        }
        return orderDtos;
	}
	
	//지정된 취소 상세정보를 출력
	public CanceledOrderDto viewCencelDetail(Long id) {
		OrderTable order = orderRepository.findById(id).orElseThrow(() -> 
    		new IllegalArgumentException("[viewCencelDetail] 주문을 찾을 수 없습니다.")
		);
		CanceledOrder canceledOrder = canceledOrderRepository.findByOrderTable(order).orElseThrow(() -> 
			new IllegalArgumentException("[viewCencelDetail] 취소상세를 찾을 수 없습니다.")
		);
			
		return CanceledOrderDto.toDto(canceledOrder);
	}
	
	//전체 주문 내역 출력
	public List<UserOrderDto> fullOrderList() {
		List<OrderTable> orders = orderRepository.findAll();
		List<UserOrderDto> userOrderDtos = new ArrayList<>();
		for(OrderTable order : orders) {
			userOrderDtos.add(UserOrderDto.toDto(order));
        }
        return userOrderDtos;
	}
	
	//날짜별 주문 내역 출력
	public List<UserOrderDto> ordersByDate(String dateString) {
		LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDateTime startOfDay = date.atStartOfDay();
		LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);
		
		List<OrderTable> orders = orderRepository.findByWriteDateBetween(startOfDay, endOfDay);
		List<UserOrderDto> userOrderDtos = new ArrayList<>();
		for(OrderTable order : orders) {
			userOrderDtos.add(UserOrderDto.toDto(order));
        }
        return userOrderDtos;
	}
	
	//결제 대기 목록 출력
	public List<OrderDto> PendingList() {
		List<OrderTable> orders = orderRepository.findByStatus(OrderStatus.PENDING);
		List<OrderDto> orderDtos = new ArrayList<>();
		for(OrderTable order : orders) {
            orderDtos.add(OrderDto.toDto(order));
        }
        return orderDtos;
	}
	
	//결제 승인
	@Transactional
	public void approvalOrder(Long id) {
		OrderTable order = orderRepository.findById(id).orElseThrow(() -> 
			new IllegalArgumentException("[approvalOrder] 주문을 찾을 수 없습니다.")
		);
		order.setStatus(OrderStatus.PAID);
		orderRepository.save(order);
	}
}


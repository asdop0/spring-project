package com.asd.DTO;

import java.math.BigDecimal;

import com.asd.model.OrderDetail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {
	private Long id;
	private String productName;
	private BigDecimal price;
	private Integer cnt;
	private String name;
	private String addr;
	private String phonenumber;
	private String req;
	
	public static OrderDetailDto toDto(OrderDetail orderDetail) {
        return new OrderDetailDto(
        		orderDetail.getId(),
        		orderDetail.getProduct().getName(),
        		orderDetail.getProduct().getPrice(),
        		orderDetail.getCnt(),
        		orderDetail.getName(),
        		orderDetail.getAddr(),
        		orderDetail.getPhonenumber(),
        		orderDetail.getReq()
        );
    }
}

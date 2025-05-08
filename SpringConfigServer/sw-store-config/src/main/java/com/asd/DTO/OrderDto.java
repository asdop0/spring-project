package com.asd.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.asd.common.OrderStatus;
import com.asd.model.OrderTable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
	private Long id; 
	private String productName;
	private BigDecimal totalPrice;
	private OrderStatus status;
	private LocalDateTime writeDate;
	
	public static OrderDto toDto(OrderTable order) {
        return new OrderDto(
        		order.getId(),
        		order.getOrderDetail().getProduct().getName(),
                order.getTotalPrice(),
                order.getStatus(),
                order.getWriteDate()
        );
    }
}

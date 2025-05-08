package com.asd.DTO;

import com.asd.model.OrderTable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderDto {
	private Long id;
	private OrderDto orderDto;
	private String userId;
	
	public static UserOrderDto toDto(OrderTable order) {
        return new UserOrderDto(
        		order.getId(),
        		OrderDto.toDto(order),
                order.getUser().getUid()
        );
    }
}

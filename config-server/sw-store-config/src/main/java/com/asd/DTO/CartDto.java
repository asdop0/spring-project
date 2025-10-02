package com.asd.DTO;

import java.math.BigDecimal;

import com.asd.model.Cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
	private Long id;
	private String productName;
	private Integer cnt;
	private BigDecimal price;
	
	public static CartDto toDto(Cart cart) {
        return new CartDto(
        		cart.getId(),
        		cart.getProduct().getName(),
        		cart.getCnt(),
        		cart.getPrice()
        );
    }
}

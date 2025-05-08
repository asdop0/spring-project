package com.asd.DTO;

import java.math.BigDecimal;

import com.asd.model.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductListDto {
	private Long id;
	private String name;
	private BigDecimal price;
	private String categoryName;
	
	public static ProductListDto toDto(Product product) {
		return new ProductListDto(
			product.getId(),
			product.getName(),
			product.getPrice(),
			product.getCategory().getName()
		);
	}
}
package com.asd.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.asd.model.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDto {
	private Long id;
	private String name;
	private String description;
	private BigDecimal price;
	private String categoryName;
	private LocalDateTime writeDate;
	private LocalDateTime updateDate;
	private List<ProductReviewDto> productReviews;
	private Long totalSales;
	
	public static ProductDetailDto toDto(Product product) {
        List<ProductReviewDto> productReviewDtos = product.getProductReviews().stream()
                .map(ProductReviewDto::toDto)
                .collect(Collectors.toList());
        
        return new ProductDetailDto(
        	product.getId(),
        	product.getName(),
        	product.getDescription(),
        	product.getPrice(),
        	product.getCategory().getName(),
        	product.getWriteDate(),
        	product.getUpdateDate(),
        	productReviewDtos,
        	product.getTotalSales()
        );
    }
}

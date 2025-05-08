package com.asd.DTO;

import java.time.LocalDateTime;

import com.asd.model.ProductReview;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductReviewDto {
	private Long id;
	private String nickname;
	private LocalDateTime writeDate;
	private String content;
	
	public static ProductReviewDto toDto(ProductReview productReview) {
		return new ProductReviewDto(
			productReview.getId(),
			productReview.getUser().getNickname(),
			productReview.getWriteDate(),
			productReview.getContent()
		);
	}
}
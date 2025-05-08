package com.asd.DTO;

import java.time.LocalDateTime;

import com.asd.model.CampingReview;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CampingReviewDto {
	private Long id;
	private String nickname;
	private LocalDateTime writeDate;
	private String content;
	
	public static CampingReviewDto toDto(CampingReview campingReview) {
		return new CampingReviewDto(
			campingReview.getId(),
			campingReview.getUser().getNickname(),
			campingReview.getWriteDate(),
			campingReview.getContent()
		);
	}
}

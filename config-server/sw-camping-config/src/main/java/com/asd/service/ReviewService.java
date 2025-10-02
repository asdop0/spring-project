package com.asd.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asd.model.Camping;
import com.asd.model.CampingReview;
import com.asd.repository.CampingRepository;
import com.asd.repository.CampingReviewRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReviewService {
	private final CampingReviewRepository campingReviewRepository;
	private final CampingRepository campingRepository;
	
	//지정된 후기 삭제
	@Transactional
	public void deleteReview(Long id) {
		CampingReview campingReview = campingReviewRepository.findById(id).orElseThrow(() -> 
			new IllegalArgumentException("[deleteReview] 해당 후기를 찾을 수 없습니다.")
		);
		Camping camping = campingReview.getCamping();
		camping.getCampingReviews().remove(campingReview);
		
		campingReviewRepository.delete(campingReview);
		campingRepository.save(camping);
	}
}

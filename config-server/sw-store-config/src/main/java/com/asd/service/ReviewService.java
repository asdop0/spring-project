package com.asd.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asd.model.Product;
import com.asd.model.ProductReview;
import com.asd.repository.ProductReviewRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReviewService {
	private final ProductReviewRepository productReviewRepository;
	
	//지정된 후기 삭제
	@Transactional
	public void deleteReview(Long id) {
		ProductReview productReview = productReviewRepository.findById(id).orElseThrow(() -> 
	    	new IllegalArgumentException("[deleteReview] 해당 후기를 찾을 수 없습니다.")
		);
		Product product = productReview.getProduct();
		product.getProductReviews().remove(productReview);
		
		productReviewRepository.delete(productReview);
	}
}

package com.asd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asd.model.ProductReview;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long>{

}

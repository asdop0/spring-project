package com.asd.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.asd.model.Category;
import com.asd.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	List<Product> findAllByStatusFalse();
	List<Product> findByCategoryAndStatusFalse(Category category);
	
	@Query("SELECT p FROM Product p WHERE p.category = :category AND p.status = false ORDER BY writeDate DESC")
	List<Product> findProductOrderByWriteDate(@Param("category") Category category);
			
	@Query("SELECT p FROM Product p LEFT JOIN p.productReviews r WHERE p.category = :category AND p.status = false GROUP BY p ORDER BY COUNT(r) DESC")
	List<Product> findProductOrderByReviewCount(@Param("category") Category category);
	
	@Query("SELECT p FROM Product p LEFT JOIN p.productBookmarks r WHERE p.category = :category AND p.status = false GROUP BY p ORDER BY COUNT(r) DESC")
	List<Product> findProductOrderByBookmarkCount(@Param("category") Category category);
	
	@Query("SELECT p FROM Product p WHERE p.category = :category AND p.status = false ORDER BY totalSales DESC")
	List<Product> findProductOrderByTotalSalesCount(@Param("category") Category category);
	
	Optional<Product> findById(Long id);
	
	@Query("SELECT p FROM Product p WHERE p.name LIKE %?1% OR p.description LIKE %?1% OR p.category.name LIKE %?1%")
    List<Product> findByNameOrDescriptionOrCategoryNameContaining(String keyword);
}

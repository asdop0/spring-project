package com.asd.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.asd.model.Camping;

public interface CampingRepository extends JpaRepository<Camping, Long>{
	List<Camping> findByDistrict(String district);
	
	@Query("SELECT s FROM com.asd.model.Camping s WHERE s.district = :district ORDER BY writeDate DESC")
	List<Camping> findCampingOrderByWriteDate(@Param("district") String district);
			
	@Query("SELECT s FROM com.asd.model.Camping s LEFT JOIN s.campingReviews c WHERE s.district = :district GROUP BY s ORDER BY COUNT(c) DESC")
	List<Camping> findCampingOrderByReviewCount(@Param("district") String district);
	
	@Query("SELECT s FROM com.asd.model.Camping s LEFT JOIN s.campingBookmarks c WHERE s.district = :district GROUP BY s ORDER BY COUNT(c) DESC")
	List<Camping> findCampingOrderByBookmarkCount(@Param("district") String district);
	
	Optional<Camping> findById(Long id);
	
	@Query("SELECT c FROM Camping c WHERE c.name LIKE %?1% OR c.address LIKE %?1%")
    List<Camping> findByNameOrAddressContaining(String keyword);
}

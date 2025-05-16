package com.asd.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.asd.model.Camping;
import com.asd.model.CampingBookmark;
import com.asd.model.User;

public interface CampingBookmarkRepository extends JpaRepository<CampingBookmark, Long>{
	@Query("SELECT s FROM com.asd.model.Camping s WHERE s IN (SELECT c.camping b FROM com.asd.model.CampingBookmark c WHERE c.user = :user)")
	List<Camping> findByBookmarkList(@Param("user") User user);
	
	Optional<CampingBookmark> findByUserAndCamping(User user, Camping camping);
}

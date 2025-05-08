package com.asd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.asd.model.Cart;
import com.asd.model.User;

public interface CartRepository extends JpaRepository<Cart, Long>{
	@Query("SELECT c FROM Cart c WHERE c.user = :user")
	List<Cart> findByCartList(@Param("user") User user);
}

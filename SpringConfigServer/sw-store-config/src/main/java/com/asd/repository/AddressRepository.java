package com.asd.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.asd.model.Address;
import com.asd.model.User;

public interface AddressRepository extends JpaRepository<Address, Long>{
	@Query("SELECT a FROM Address a WHERE a.user = :user")
	List<Address> findByAddressList(@Param("user") User user);
	
	Optional<Address> findById(Long id);
	
	Optional<Address> findByUserAndChoice(User user, String Choice);
	
	@Query("SELECT COUNT(a) FROM Address a")
	Long countAddress();
}

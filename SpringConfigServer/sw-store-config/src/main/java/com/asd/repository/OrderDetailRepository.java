package com.asd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asd.model.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>{

}

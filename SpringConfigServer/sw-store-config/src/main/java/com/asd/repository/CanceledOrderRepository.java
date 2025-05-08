package com.asd.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asd.model.CanceledOrder;
import com.asd.model.OrderTable;

public interface CanceledOrderRepository extends JpaRepository<CanceledOrder, Long>{
	Optional<CanceledOrder> findByOrderTable(OrderTable orderTable);
}

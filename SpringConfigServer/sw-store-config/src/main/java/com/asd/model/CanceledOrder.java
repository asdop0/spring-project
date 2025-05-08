package com.asd.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.asd.common.OrderCancelStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CanceledOrder {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="User_id")
	private User user;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="OrderTable_id")
	private OrderTable orderTable;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="Product_id")
	private Product product;
	
	private String reason;
	
	@Enumerated(EnumType.STRING)
	private OrderCancelStatus status;
	
	@CreationTimestamp
	@Column(name="write_date")
	private LocalDateTime writeDate;
	
	@Column(name="approval_date")
	private LocalDateTime approvalDate;
	
	@Column(nullable = false, name = "total_price")
	private BigDecimal totalPrice;
}

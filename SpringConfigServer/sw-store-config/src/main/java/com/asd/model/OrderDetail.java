package com.asd.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class OrderDetail {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="OrderTable_id")
	private OrderTable orderTable;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="Product_id")
	private Product product;
	
	@Column(nullable = false)
	private BigDecimal price;
	
	@Column(nullable = false)
	private Integer cnt;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String addr;
	
	@Column(nullable = false)
	private String phonenumber;
	
	@Column(nullable = true)
	private String req;
}

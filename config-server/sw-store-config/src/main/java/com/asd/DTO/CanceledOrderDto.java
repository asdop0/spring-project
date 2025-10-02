package com.asd.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.asd.model.CanceledOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CanceledOrderDto {
	private Long id;
	private String productName;
	private String reason;
	private String status;
	private LocalDateTime writeDate;
	private LocalDateTime approvalDate;
	private BigDecimal totalPrice;
	
	public static CanceledOrderDto toDto(CanceledOrder canceledOrder) {
		return new CanceledOrderDto(
			canceledOrder.getId(),
			canceledOrder.getProduct().getName(),
			canceledOrder.getReason(),
			canceledOrder.getStatus().toString(),
			canceledOrder.getWriteDate(),
			canceledOrder.getApprovalDate(),
			canceledOrder.getTotalPrice()
		);
	}
}

package com.asd.DTO;

import java.time.LocalDateTime;

import com.asd.model.Camping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CampingListDto {
	private Long id;
	private String name;
	private LocalDateTime writeDate;
	private String address;
	private double latitude;
	private double longitude;
	
	public static CampingListDto toDto(Camping camping) {
		return new CampingListDto(
			camping.getId(),
			camping.getName(),
			camping.getWriteDate(),
			camping.getAddress(),	
			camping.getLatitude(),
			camping.getLongitude()
		);
	}
}

package com.asd.DTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.asd.model.Camping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CampingDetailDto {
	private Long id;
	private String name;
	private LocalDateTime writeDate;
	private String address;
	private double latitude;
	private double longitude;
	private String phonenumber;
	private String homepage;
	private String district;
	private List<CampingReviewDto> campingReviews;
	
	public static CampingDetailDto toDto(Camping camping) {
        List<CampingReviewDto> campingReviewDtos = camping.getCampingReviews().stream()
                .map(CampingReviewDto::toDto)
                .collect(Collectors.toList());
        
        return new CampingDetailDto(
        	camping.getId(),
        	camping.getName(),
        	camping.getWriteDate(),
        	camping.getAddress(),
        	camping.getLatitude(),
        	camping.getLongitude(),
        	camping.getPhonenumber(),
        	camping.getHomepage(),
        	camping.getDistrict(),
        	campingReviewDtos
        );
    }
}

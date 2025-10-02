package com.asd.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CampingRequestDto {
	private String id;
	private String name;
	private String address;
	private String district;
	private String homepage;
	private String latitude;
	private String longitude;
	private String phonenumber;
}

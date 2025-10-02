package com.asd.DTO;

import com.asd.model.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
	private Long id;
	private String name;
	private String addr;
	private String phonenumber;
	private String req;
	private String choice;
	
	public static AddressDto toDto(Address address) {
		return new AddressDto(
			address.getId(),
			address.getName(),
			address.getAddr(),
			address.getPhonenumber(),
			address.getReq(),
			address.getChoice()
		);
	}
}

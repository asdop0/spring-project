 package com.asd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asd.DTO.AddressDto;
import com.asd.model.Address;
import com.asd.model.User;
import com.asd.repository.AddressRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AddressService {
	private final AddressRepository addressRepository;
	
	//전달받은 배송지 저장
	@Transactional
	public void addAddress(Address address, User user) {
		if(addressRepository.countAddress() == 3) {
			new IllegalArgumentException("배송지를 추가할 수 없습니다.");
		}
		
		try {
			Optional<Address> preAddress = addressRepository.findByUserAndChoice(user, "O");
			Address preAddress_ = null;
			if(preAddress.isPresent()) {
				preAddress_ = preAddress.get();
				preAddress_.setChoice("X");
				addressRepository.save(preAddress_);
			}
		}catch(Exception e) {
			
		}	
		addressRepository.save(address);
	}
	
	//전달받은 배송지의 수정 정보로 저장
	public void modifyAddress(Address address) {
		Address address_ = addressRepository.findById(address.getId()).orElseThrow(() -> 
        	new IllegalArgumentException("[modifyAddress] 수정할 배송지를 찾을 수 없습니다.")
		); //기존의 배송지 정보를 가져옴;
		
		address_.setName(address.getName());
		address_.setPhonenumber(address.getPhonenumber());
		address_.setReq(address.getReq());
		address_.setAddr(address.getAddr());
		
		addressRepository.save(address_);		
	}
	
	//지정된 배송지 삭제
	public void deleteAddress(Long id) {
		addressRepository.deleteById(id);
	}
	
	//배송지 전체 리스트 출력
	public List<AddressDto> addressList(User user) {
		List<Address> addresses = addressRepository.findByAddressList(user);	
		List<AddressDto> addressDtos = new ArrayList<>();
		for(Address address : addresses) {
			addressDtos.add(AddressDto.toDto(address));
        }
        return addressDtos;
	}
	
	//지정된 배송지의 상세정보를 출력
	public AddressDto viewAddress(Long id) {
		Address address = addressRepository.findById(id).orElseThrow(() -> 
    		new IllegalArgumentException("[viewAddress] 배송지를 찾을 수 없습니다.")
		);
		return AddressDto.toDto(address);
	}
	
	//기본 배송지 출력
	public Address getAddress(User user) {
		Address address = addressRepository.findByUserAndChoice(user, "O").orElseThrow(() -> 
    		new IllegalArgumentException("[getAddress] 배송지를 찾을 수 없습니다.")
		);
		return address;
	}
	
	//기본 배송지 변경
	@Transactional
	public void choiceAddress(User user, Long id) {
		Optional<Address> optionalAddress = addressRepository.findByUserAndChoice(user, "O");

		if (optionalAddress.isPresent()) {
		    Address address = optionalAddress.get();
		    address.setChoice("X");
		    addressRepository.save(address);
		}
		
		Address newAddress = addressRepository.findById(id).orElseThrow(() -> 
			new IllegalArgumentException("[choiceAddress] 새로운 배송지를 찾을 수 없습니다.")
		);
		newAddress.setChoice("O");
		addressRepository.save(newAddress);
	}
}

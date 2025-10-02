package com.asd.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asd.DTO.CampingRequestDto;
import com.asd.model.Camping;
import com.asd.service.CampingService;
import com.asd.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/campingAdmin")
public class AdminController {
	private final CampingService campingService;
	private final ReviewService reviewService;
	private Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	//캠핑장 삭제
	@DeleteMapping("/delete")
	public Map<String, String> deleteCamping(@RequestParam String camping_id) {
		campingService.deleteCamping(Long.parseLong(camping_id));
		Map<String, String> response = new HashMap<>();
		response.put("check", "true");
		logger.info("[deleteCamping] {} 캠핑장이 삭제되었습니다.", camping_id);
    	return response;
	}
	
	//캠핑장 등록
	@PostMapping("/add")
	public Map<String, String> addCamping(@RequestBody CampingRequestDto campingRequestDto) {
		Camping camping = new Camping(); //캠핑장 정보 삽입
		camping.setName(campingRequestDto.getName());
		camping.setAddress(campingRequestDto.getAddress());
		camping.setDistrict(campingRequestDto.getDistrict());
		camping.setHomepage(campingRequestDto.getHomepage());
		camping.setLatitude(Double.parseDouble(campingRequestDto.getLatitude()));
		camping.setWriteDate(LocalDateTime.now());
		camping.setLongitude(Double.parseDouble(campingRequestDto.getLongitude()));
		camping.setPhonenumber(campingRequestDto.getPhonenumber());
		
		campingService.addCamping(camping);
		Map<String, String> response = new HashMap<>();
		response.put("check", "true");
		logger.info("[addCamping] {} 캠핑장이 등록되었습니다.", campingRequestDto.getName());
    	return response;
	}
	
	//캠핑장 정보 수정
	@PostMapping("/modify")
	public Map<String, String> modifyCamping(@RequestBody CampingRequestDto campingRequestDto) {
		Camping camping = new Camping(); //캠핑장 수정 정보 삽입
		camping.setId(Long.parseLong(campingRequestDto.getId()));	
		camping.setName(campingRequestDto.getName());
		camping.setAddress(campingRequestDto.getAddress());
		camping.setDistrict(campingRequestDto.getDistrict());
		camping.setHomepage(campingRequestDto.getHomepage());
		camping.setLatitude(Double.parseDouble(campingRequestDto.getLatitude()));
		camping.setLongitude(Double.parseDouble(campingRequestDto.getLongitude()));
		camping.setPhonenumber(campingRequestDto.getPhonenumber());
		campingService.modifyCamping(camping);
		Map<String, String> response = new HashMap<>();
		response.put("check", "true");
		logger.info("[modifyCamping] {} 캠핑장이 수정되었습니다.", campingRequestDto.getId());
    	return response;
	}
	
	//후기 삭제
	@DeleteMapping("/delete/review")
	public Map<String, String> deleteReview(@RequestParam String review_id) {
		reviewService.deleteReview(Long.parseLong(review_id));
		Map<String, String> response = new HashMap<>();
		response.put("check", "true");
		logger.info("[deleteReview] 관리자에 의해 {} 후기가 삭제되었습니다.", review_id);
    	return response;
	}
}

package com.asd.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asd.DTO.CampingDetailDto;
import com.asd.DTO.CampingListDto;
import com.asd.service.CampingService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/camping")
public class CampingController {
	private final CampingService campingService;	
	private Logger logger = LoggerFactory.getLogger(CampingController.class);
	
	//캠핑장 전체 리스트 조회
	@GetMapping("/list")
	public List<CampingListDto> getCampingList() {
		return campingService.campingList();
	}
	
	//해당 지역의 캠핑장 리스트 조회
	@GetMapping("/district")
	public List<CampingListDto> getDistrictList(@RequestParam String district) {
		logger.info("[getDistrictList] {} 지역의 캠핑장을 조회합니다.", district);
		return campingService.districtList(district);
	}
	
	//조건에 따른 정렬
	@GetMapping("/sort")
	public List<CampingListDto> getSortList(@RequestParam String district, @RequestParam String condition) {
		logger.info("[getSortList] {}로 조회했습니다.", condition);
		switch(condition) {
		case "update" : //최신순
			return campingService.updateList(district);
		case "review" : //후기순
			return campingService.reviewList(district);
		case "bookmart" : //즐겨찾기순
			return campingService.bookmartList(district);
		default :
			return campingService.districtList(district);
		}
	}
	
	//캠핑장 상세정보
	@GetMapping("/view")
	public CampingDetailDto viewCamping(@RequestParam String camping_id) {
		return campingService.viewCamping(Long.parseLong(camping_id));
	}
	
	//캠핑장 검색
	@GetMapping("/search")
	public List<CampingListDto> getSearchCampingList(@RequestParam String search) {
		logger.info("[CampingListDto] 사용자가 {}을 검색하였습니다.", search);
		return campingService.searchCamping(search);
	}
}

package com.asd.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asd.DTO.CampingDetailDto;
import com.asd.DTO.CampingListDto;
import com.asd.service.CampingService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/campings")
public class CampingController {
	private final CampingService campingService;	
	private Logger logger = LoggerFactory.getLogger(CampingController.class);

	@GetMapping
	public List<CampingListDto> getCampings(
	    @RequestParam(required = false) String search,
	    @RequestParam(required = false) String district,
	    @RequestParam(required = false) String sortCondition
	) {
	    if (search != null && !search.isEmpty()) {
	        logger.info("[getCampings] 검색어로 조회: {}", search);
	        return campingService.searchCamping(search);
	    }
	    
	    if (district != null && !district.isEmpty()) {
	        if ("update".equals(sortCondition)) {
	            logger.info("[getCampings] {} 지역 최신순 정렬", district);
	            return campingService.updateList(district);
	        } else if ("review".equals(sortCondition)) {
	            logger.info("[getCampings] {} 지역 후기순 정렬", district);
	            return campingService.reviewList(district);
	        } else if ("bookmark".equals(sortCondition)) {
	            logger.info("[getCampings] {} 지역 즐겨찾기순 정렬", district);
	            return campingService.bookmartList(district);
	        } else {
	            logger.info("[getCampings] {} 지역 기본 조회", district);
	            return campingService.districtList(district);
	        }
	    }
	    
	    logger.info("[getCampings] 전체 캠핑장 리스트 조회");
	    return campingService.campingList();
	}
	
	//캠핑장 전체 리스트 조회 + 캐시 X
	@GetMapping("/list")
	public List<CampingListDto> getCampingList2() {
		return campingService.campingList2();
	}

	
	//캠핑장 상세정보
	@GetMapping("/{camping_id}")
	public CampingDetailDto viewCamping(@PathVariable String camping_id) {
		return campingService.viewCamping(Long.parseLong(camping_id));
	}
}

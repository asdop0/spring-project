package com.asd.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.asd.DTO.CampingDetailDto;
import com.asd.DTO.CampingListDto;
import com.asd.model.Camping;
import com.asd.repository.CampingRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CampingService {
	private final CampingRepository campingRepository;
	
	private Logger logger = LoggerFactory.getLogger(CampingService.class);
	
	//캠핑장 전체 리스트 출력
	public List<CampingListDto> campingList() {
		List<Camping> campings = campingRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));	
		List<CampingListDto> campingListDtos = new ArrayList<>();
		for(Camping camping : campings) {
			campingListDtos.add(CampingListDto.toDto(camping));
        }
		
		return campingListDtos;
	}
	
	//해당 구역의 캠핑장 리스트 출력
	public List<CampingListDto> districtList(String district) {
		List<Camping> campings = campingRepository.findByDistrict(district);
		List<CampingListDto> campingListDtos = new ArrayList<>();
		for(Camping camping : campings) {
			campingListDtos.add(CampingListDto.toDto(camping));
        }
		
		return campingListDtos;
	}
	
	//해당 구역의 캠핑장을 최신순으로 출력
	public List<CampingListDto> updateList(String district) {
		List<Camping> campings = campingRepository.findCampingOrderByWriteDate(district);
		List<CampingListDto> campingListDtos = new ArrayList<>();
		for(Camping camping : campings) {
			campingListDtos.add(CampingListDto.toDto(camping));
        }
		
		return campingListDtos;
	}
	
	//해당 구역의 캠핑장을 후기가 많은 순으로 출력
	public List<CampingListDto> reviewList(String district) {
		List<Camping> campings = campingRepository.findCampingOrderByReviewCount(district);
		List<CampingListDto> campingListDtos = new ArrayList<>();
		for(Camping camping : campings) {
			campingListDtos.add(CampingListDto.toDto(camping));
        }
		
		return campingListDtos;
	}
	
	//해당 구역의 캠핑장을 즐겨찾기가 많은 순으로 출력
	public List<CampingListDto> bookmartList(String district) {
		List<Camping> campings = campingRepository.findCampingOrderByBookmarkCount(district);
		List<CampingListDto> campingListDtos = new ArrayList<>();
		for(Camping camping : campings) {
			campingListDtos.add(CampingListDto.toDto(camping));
        }
		
		return campingListDtos;
	}
	
	//지정된 캠핑장의 상세정보를 출력
	public CampingDetailDto viewCamping(Long id) {
		Camping camping = campingRepository.findById(id).orElseThrow(() -> 
    		new IllegalArgumentException("[viewCamping] 캠핑장을 찾을 수 없습니다.")
		);
		return CampingDetailDto.toDto(camping);
	}
	
	//지정된 캠핑장 삭제
	public void deleteCamping(Long id) {
		campingRepository.deleteById(id);
	}
	
	//전달받은 캠핑장 저장
	public void addCamping(Camping camping) {
		campingRepository.save(camping);
	}
	
	//전달받은 캠핑장의 수정 정보로 저장
	public void modifyCamping(Camping camping) {
		Camping camping_ = campingRepository.findById(camping.getId()).orElseThrow(() -> 
        	new IllegalArgumentException("[modifyCamping] 수정할 캠핑장을 찾을 수 없습니다.")
		);
		
		camping_.setName(camping.getName());
		camping_.setHomepage(camping.getHomepage());
		camping_.setPhonenumber(camping.getPhonenumber());
		camping_.setLatitude(camping.getLatitude());
		camping_.setLongitude(camping.getLongitude());
		camping_.setAddress(camping.getAddress());
		camping_.setDistrict(camping.getDistrict());
		
		campingRepository.save(camping_);		
	}
	
	//지정된 캠핑장 가져오기
	public Camping getCamping(Long id) {
		Camping camping = campingRepository.findById(id).orElseThrow(() -> 
    		new IllegalArgumentException("[viewCamping] 캠핑장을 찾을 수 없습니다.")
		);
		return camping;
	}
	
	//캠핑장 검색
	public List<CampingListDto> searchCamping(String search) {
		String[] keywords = search.split("\\s+");
		HashMap<Camping, Integer> map = new HashMap<>();
		int count = 0;
		
		for (String keyword : keywords) { //입력된 검색어 앞부분 7개만
			if(count == 7) {
				break;
			}
			logger.info("검색어: {}", keyword);
			List<Camping> campings = campingRepository.findByNameOrAddressContaining(keyword);
			for (Camping camping : campings) { //자주 등장하는 결과물 체크
				int importance = 0;
			    
			    if (camping.getName().contains(keyword)) { //이름에서 발견된 결과이면 중요도가 높음
			        importance = 2;
			    }
			    
			    if ((camping.getAddress() != null) && camping.getAddress().contains(keyword)) {
			        importance = Math.max(importance, 1);
			    }

			    map.put(camping, map.getOrDefault(camping, 0) + importance);
			}
			count++;
		}
		
		List<Camping> campings = map.entrySet().stream() //검색어가 많은 순으로 정렬된 리스트
	            .sorted((entry1, entry2) -> entry2.getValue() - entry1.getValue()) // value 기준 내림차순 정렬
	            .map(Map.Entry::getKey) // Camping 객체만 추출
	            .collect(Collectors.toList());
        
		List<CampingListDto> campingListDtos = new ArrayList<>();
		for(Camping camping : campings) {
			campingListDtos.add(CampingListDto.toDto(camping));
        }
		
		return campingListDtos;
	}
}

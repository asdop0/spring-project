package com.asd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asd.DTO.CampingListDto;
import com.asd.model.Camping;
import com.asd.model.CampingBookmark;
import com.asd.model.User;
import com.asd.repository.CampingBookmarkRepository;
import com.asd.repository.CampingRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookmarkService {
	private final CampingBookmarkRepository campingBookmarkRepository;
	private final CampingRepository campingRepository;
	
	//해당 유저의 북마크 리스트 조회
	public List<CampingListDto> bookmarkList(User user) {
		List<Camping> bookmarks = campingBookmarkRepository.findByBookmarkList(user);
		List<CampingListDto> bookmarkListDtos = new ArrayList<>();
		for(Camping bookmark : bookmarks) {
			bookmarkListDtos.add(CampingListDto.toDto(bookmark));
        }
		
		return bookmarkListDtos;
	}
	
	//지정된 북마크 찾기
	public Optional<CampingBookmark> getBookmark(User user, Camping camping) {
		return campingBookmarkRepository.findByUserAndCamping(user, camping);
	}
	
	//지정된 북마크 삭제
	@Transactional
	public void deleteBookmark(User user, Camping camping) {
		CampingBookmark campingBookmark = campingBookmarkRepository.findByUserAndCamping(user, camping).orElseThrow(() -> 
	    	new IllegalArgumentException("[deleteBookmark] 해당 즐겨찾기를 찾을 수 없습니다.")
		);
		Camping camping_ = campingBookmark.getCamping();
		camping_.getCampingBookmarks().remove(campingBookmark);
		
		campingBookmarkRepository.delete(campingBookmark);
		campingRepository.save(camping_);
	}
}

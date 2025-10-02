package com.asd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asd.DTO.ProductListDto;
import com.asd.model.Product;
import com.asd.model.ProductBookmark;
import com.asd.model.User;
import com.asd.repository.ProductBookmarkRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookmarkService {
	private final ProductBookmarkRepository productBookmarkRepository;
	
	//해당 유저의 북마크 리스트 조회
	public List<ProductListDto> bookmarkList(User user) {
		List<Product> products = productBookmarkRepository.findByBookmarkList(user);
		List<ProductListDto> productListDtos = new ArrayList<>();
		for(Product product : products) {
			productListDtos.add(ProductListDto.toDto(product));
        }
		
		return productListDtos;
	}
	
	//지정된 북마크 찾기
	public Optional<ProductBookmark> getBookmark(User user, Product product) {
		return productBookmarkRepository.findByUserAndProduct(user, product);
	}
	
	//지정된 북마크 삭제
	@Transactional
	public void deleteBookmark(User user, Product product) {
		ProductBookmark productBookmark = productBookmarkRepository.findByUserAndProduct(user, product).orElseThrow(() -> 
	    	new IllegalArgumentException("[deleteBookmark] 해당 즐겨찾기를 찾을 수 없습니다.")
		);
		Product product_ = productBookmark.getProduct();
		product_.getProductBookmarks().remove(productBookmark);
		
		productBookmarkRepository.delete(productBookmark);
	}
}

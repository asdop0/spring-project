package com.asd.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asd.DTO.ProductDetailDto;
import com.asd.DTO.ProductListDto;
import com.asd.model.Category;
import com.asd.model.Product;
import com.asd.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {
	private final ProductRepository productRepository;
	
	private Logger logger = LoggerFactory.getLogger(ProductService.class);
	
	//상품 전체 리스트 출력
	public List<ProductListDto> productList() {
		List<Product> products = productRepository.findAllByStatusFalse();	
		List<ProductListDto> productListDtos = new ArrayList<>();
		for(Product product : products) {
			productListDtos.add(ProductListDto.toDto(product));
        }
		
		return productListDtos;
	}
	
	//해당 카테고리의 상품 리스트 출력
	public List<ProductListDto> categoryList(Category category) {
		List<Product> products = productRepository.findByCategoryAndStatusFalse(category);
		List<ProductListDto> productListDtos = new ArrayList<>();
		for(Product product : products) {
			productListDtos.add(ProductListDto.toDto(product));
        }
		
		return productListDtos;
	}
	
	//해당 카테고리의 상품을 최신순으로 출력
	public List<ProductListDto> updateList(Category category) {
		List<Product> products = productRepository.findProductOrderByWriteDate(category);
		List<ProductListDto> productListDtos = new ArrayList<>();
		for(Product product : products) {
			productListDtos.add(ProductListDto.toDto(product));
        }
		
		return productListDtos;
	}
	
	//해당 카테고리의 상품을 후기가 많은 순으로 출력
	public List<ProductListDto> reviewList(Category category) {
		List<Product> products = productRepository.findProductOrderByReviewCount(category);
		List<ProductListDto> productListDtos = new ArrayList<>();
		for(Product product : products) {
			productListDtos.add(ProductListDto.toDto(product));
        }
		
		return productListDtos;
	}
	
	//해당 카테고리의 상품을 즐겨찾기가 많은 순으로 출력
	public List<ProductListDto> bookmartList(Category category) {
		List<Product> products = productRepository.findProductOrderByBookmarkCount(category);
		List<ProductListDto> productListDtos = new ArrayList<>();
		for(Product product : products) {
			productListDtos.add(ProductListDto.toDto(product));
        }
		
		return productListDtos;
	}
	
	//해당 카테고리의 상품을 판매량이 많은 순으로 출력
	public List<ProductListDto> totalSalesList(Category category) {
		List<Product> products = productRepository.findProductOrderByTotalSalesCount(category);
		List<ProductListDto> productListDtos = new ArrayList<>();
		for(Product product : products) {
			productListDtos.add(ProductListDto.toDto(product));
        }
		
		return productListDtos;
	}
	
	//지정된 상품의 상세정보를 출력
	public ProductDetailDto viewProduct(Long id) {
		Product product = productRepository.findById(id).orElseThrow(() -> 
    		new IllegalArgumentException("[viewProduct] 상품을 찾을 수 없습니다.")
		);
		return ProductDetailDto.toDto(product);
	}
	
	//지정된 상품을 출력
	public Product getProduct(Long id) {
		Product product = productRepository.findById(id).orElseThrow(() -> 
    		new IllegalArgumentException("[viewProduct] 상품을 찾을 수 없습니다.")
		);
		return product;
	}
	
	//지정된 상품 삭제
	public void deleteProduct(Long id) {
		Product product = productRepository.findById(id).orElseThrow(() -> 
			new IllegalArgumentException("[deleteProduct] 상품을 찾을 수 없습니다.")
		);
		product.setStatus(true);
		productRepository.save(product);
	}
	
	//전달받은 상품 저장
	public void addProduct(Product product) {
		productRepository.save(product);
	}
	
	//전달받은 상품의 수정 정보로 저장
	public void modifyProduct(Product product) {
		Product product_ = productRepository.findById(product.getId()).orElseThrow(() -> 
        	new IllegalArgumentException("[modifyProduct] 수정할 상품을 찾을 수 없습니다.")
		); //기존의 상품 정보를 가져옴;
		
		product_.setName(product.getName());
		product_.setDescription(product.getDescription());
		product_.setPrice(product.getPrice());
		product_.setCategory(product.getCategory());
		product_.setUpdateDate(LocalDateTime.now());
		
		productRepository.save(product_);		
	}
	
	//상품 검색
	public List<ProductListDto> searchProduct(String search) {
		String[] keywords = search.split("\\s+");
		HashMap<Product, Integer> map = new HashMap<>();
		int count = 0;
		
		for (String keyword : keywords) { //입력된 검색어 앞부분 7개만
			if(count == 7) {
				break;
			}
			logger.info("검색어: {}", keyword);
			List<Product> products = productRepository.findByNameOrDescriptionOrCategoryNameContaining(keyword);
			for (Product product : products) { //자주 등장하는 결과물 체크
				int importance = 0;
			    
			    if (product.getName().contains(keyword)) { //이름에서 발견된 결과이면 중요도가 높음
			        importance = 3;
			    }
			    
			    if (product.getCategory().getName().contains(keyword)) {
			        importance = Math.max(importance, 2);
			    }
			    
			    if (product.getDescription().contains(keyword)) {
			        importance = Math.max(importance, 1);
			    }

			    map.put(product, map.getOrDefault(product, 0) + importance);
			}
			count++;
		}
		
		List<Product> products = map.entrySet().stream() //검색어가 많은 순으로 정렬된 리스트
	            .sorted((entry1, entry2) -> entry2.getValue() - entry1.getValue()) // value 기준 내림차순 정렬
	            .map(Map.Entry::getKey) // Camping 객체만 추출
	            .collect(Collectors.toList());
        
		List<ProductListDto> productListDtos = new ArrayList<>();
		for(Product product : products) {
			productListDtos.add(ProductListDto.toDto(product));
        }
		
		return productListDtos;
	}
}

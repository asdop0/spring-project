package com.asd.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asd.DTO.CategoryDto;
import com.asd.model.Category;
import com.asd.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {
	private final CategoryService categoryService;
	
	//카테고리 추가
	@PostMapping("/add")
	public Map<String, String> addCategory(@RequestParam String name) {
		Category category = new Category(); //카테고리 정보 삽입
		category.setName(name);
		categoryService.addCategory(category);
		Map<String, String> response = new HashMap<>();
		response.put("check", "true");
    	return response;
	}
	
	//카테고리 수정
	@PostMapping("/modify")
	public Map<String, String> modifyCategory(@RequestParam String category_id, @RequestParam String name) {
		Category category = new Category(); //카테고리 수정 정보 삽입
		category.setId(Long.parseLong(category_id));	
		category.setName(name);
		categoryService.modifyCategory(category);
		Map<String, String> response = new HashMap<>();
		response.put("check", "true");
    	return response;
	}
	
	//카테고리 삭제
	@DeleteMapping("/delete")
	public Map<String, String> deleteCategory(@RequestParam String category_id) {
		categoryService.deleteCategory(Long.parseLong(category_id));
		Map<String, String> response = new HashMap<>();
		response.put("check", "true");
    	return response;
	}
	
	//카테고리 목록 출력
	@GetMapping("/list")
	public List<CategoryDto> getCategoryList() {
		return categoryService.categoryList();
	}
}

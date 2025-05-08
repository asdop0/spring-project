package com.asd.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asd.model.Camping;
import com.asd.model.CampingReview;
import com.asd.model.User;
import com.asd.service.CampingService;
import com.asd.service.ReviewService;
import com.asd.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/campingReview")
public class ReviewController {
	private final CampingService campingService;
	private final UserService userService;
	private final ReviewService reviewService;
	private Logger logger = LoggerFactory.getLogger(ReviewController.class);
	
	//후기 등록
	@PostMapping("/add")
	public Map<String, String> addReview(HttpServletRequest request, @RequestParam String camping_id, @RequestParam String content) {
		User user = userService.findUser(request); //유저 정보 추출
		
		CampingReview campingReview = new CampingReview(); //후기 정보 삽입
		campingReview.setContent(content);
		campingReview.setUser(user);
		
		Camping camping = campingService.getCamping(Long.parseLong(camping_id)); //해당 캠핑장 추출
		camping.addCampingReview(campingReview);

		campingService.addCamping(camping); //cascade를 통한 후기 저장
		Map<String, String> response = new HashMap<>();
		response.put("check", "true");
		logger.info("[addReview] {} 사용자가 {} 캠핑의 후기를 작성했습니다.", user.getId(), camping_id);
    	return response;
	}
	
	//후기 삭제
	@DeleteMapping("/delete")
	public Map<String, String> deleteReview(@RequestParam String review_id) {
		reviewService.deleteReview(Long.parseLong(review_id));
		Map<String, String> response = new HashMap<>();
		response.put("check", "true");
		logger.info("[deleteReview] 사용자가 {} 후기를 삭제합니다.", review_id);
    	return response;
	}
}

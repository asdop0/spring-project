package com.asd.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asd.service.BoardService;
import com.asd.service.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boardAdmin")
public class AdminController {
	private final BoardService boardService;
	private final CommentService commentService;
	private Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	//게시글 삭제
	@DeleteMapping("/deleteBoard")
	public Map<String, String> deleteBoard(@RequestParam String board_id) {
		boardService.deleteBoard(Long.parseLong(board_id));
		Map<String, String> response = new HashMap<>();
		response.put("check", "true");
		logger.info("[deleteBoard] 관리자가 게시글을 삭제했습니다.");
    	return response;
	}
	
	//댓글 삭제
	@DeleteMapping("/deleteComment")
	public Map<String, String> deleteComment(@RequestParam String comment_id) {
		commentService.deleteComment(Long.parseLong(comment_id));
		Map<String, String> response = new HashMap<>();
		response.put("check", "true");
		logger.info("[deleteComment] 관리자가 댓글을 삭제했습니다.");
    	return response;
	}
}

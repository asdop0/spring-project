package com.asd.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asd.model.Board;
import com.asd.model.Comment;
import com.asd.model.User;
import com.asd.service.BoardService;
import com.asd.service.CommentService;
import com.asd.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
	private final BoardService boardService;
	private final UserService userService;
	private final CommentService commentService;
	
	//댓글 등록
	@PostMapping("/add")
	public Map<String, String> addComment(HttpServletRequest request, @RequestParam String board_id
			, @RequestParam String i_comment) {
		User user = userService.findUser(request); //유저 정보 추출
		
		Comment comment = new Comment(); //댓글 정보 삽입
		comment.setComment(i_comment);
		comment.setUser(user);
		
		Board board = boardService.getBoard(Long.parseLong(board_id));
		board.addComment(comment);
		
		boardService.addBoard(board); //cascade를 통한 comment 저장
		
		Map<String, String> response = new HashMap<>();
		response.put("check", "true");
    	return response;
	}
	
	//댓글 삭제
	@DeleteMapping("/delete")
	public Map<String, String> deleteComment(@RequestParam String comment_id) {
		commentService.deleteComment(Long.parseLong(comment_id));
		Map<String, String> response = new HashMap<>();
		response.put("check", "true");
    	return response;
	}
}

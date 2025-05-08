package com.asd.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asd.model.Board;
import com.asd.model.Comment;
import com.asd.repository.BoardRepository;
import com.asd.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
	private final BoardRepository boardRepository;
	
	//지정된 댓글 삭제
	@Transactional
	public void deleteComment(Long id) {
		Comment comment = commentRepository.findById(id).orElseThrow(() -> 
			new IllegalArgumentException("[deleteComment] 해당 댓글을 찾을 수 없습니다.")
		);
		Board board = comment.getBoard();
		board.getComments().remove(comment);
		
		commentRepository.delete(comment);
		boardRepository.save(board);
	}
}

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

import com.asd.DTO.BoardDetailDto;
import com.asd.DTO.BoardListDto;
import com.asd.model.Board;
import com.asd.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	private final BoardRepository boardRepository;
	
	private Logger logger = LoggerFactory.getLogger(BoardService.class);
	
	//게시글 전체 리스트 출력
	public List<BoardListDto> boardList() {
		List<Board> boards = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
		List<BoardListDto> boardListDtos = new ArrayList<>();
		for(Board board : boards) {
			boardListDtos.add(BoardListDto.toDto(board));
        }
		
		return boardListDtos;
	}
	
	//게시글을 조회순으로 출력
	public List<BoardListDto> viewList() {
		List<Board> boards = boardRepository.findByViewList();
		List<BoardListDto> boardListDtos = new ArrayList<>();
		for(Board board : boards) {
			boardListDtos.add(BoardListDto.toDto(board));
        }
		
		return boardListDtos;
	}
	
	//게시글을 댓글순으로 출력
	public List<BoardListDto> commentList() {
		List<Board> boards = boardRepository.findByCommentList();
		List<BoardListDto> boardListDtos = new ArrayList<>();
		for(Board board : boards) {
			boardListDtos.add(BoardListDto.toDto(board));
        }
		
		return boardListDtos;
	}
	
	//전달받은 게시글 저장
	public void addBoard(Board board) {
		boardRepository.save(board);
	}
	
	//지정된 게시글의 상세정보 출력
	public BoardDetailDto viewBoard(Long id) {
		Board board = boardRepository.findById(id).orElseThrow(() -> 
            new IllegalArgumentException("[viewBoard] 게시글을 찾을 수 없습니다.")
        );
		
		board.setCount(board.getCount() + 1L);
	    boardRepository.save(board);
		
		return BoardDetailDto.toDto(board);
	}
	
	//지정된 게시글 삭제
	public void deleteBoard(Long id) {
		boardRepository.deleteById(id);
	}
	
	//지정된 게시글 가져오기
	public Board getBoard(Long id) {
		Board board = boardRepository.findById(id).orElseThrow(() -> 
        	new IllegalArgumentException("[viewBoard] 게시글을 찾을 수 없습니다.")
		);
		
		return board;
	}
	
	//게시글 검색
	public List<BoardListDto> searchBoard(String search) {
		String[] keywords = search.split("\\s+");
		HashMap<Board, Integer> map = new HashMap<>();
		int count = 0;
		
		for (String keyword : keywords) { //입력된 검색어 앞부분 7개만
			if(count == 7) {
				break;
			}
			logger.info("검색어: {}", keyword);
			List<Board> boards = boardRepository.findByTitleContaining(keyword);
			for (Board board : boards) { //자주 등장하는 결과물 체크
				 map.put(board, map.getOrDefault(board, 0) + 1);
			}
			count++;
		}
		
		List<Board> boards = map.entrySet().stream() //검색어가 많은 순으로 정렬된 리스트
	            .sorted((entry1, entry2) -> entry2.getValue() - entry1.getValue()) // value 기준 내림차순 정렬
	            .map(Map.Entry::getKey) // Camping 객체만 추출
	            .collect(Collectors.toList());
        
		List<BoardListDto> boardListDtos = new ArrayList<>();
		for(Board board : boards) {
			boardListDtos.add(BoardListDto.toDto(board));
        }
		
		return boardListDtos;
	}
}

package com.asd.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.asd.model.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{
	Optional<Board> findById(Long id);
	
	@Query("SELECT b FROM Board b ORDER BY count DESC")
	List<Board> findByViewList();
			
	@Query("SELECT b FROM Board b LEFT JOIN b.comments c GROUP BY b ORDER BY COUNT(c) DESC")
	List<Board> findByCommentList();
	
	@Query("SELECT b FROM Board b WHERE b.title LIKE %?1%")
    List<Board> findByTitleContaining(String keyword);
}

package com.asd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asd.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
}

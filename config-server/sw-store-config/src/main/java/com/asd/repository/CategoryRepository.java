package com.asd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asd.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}

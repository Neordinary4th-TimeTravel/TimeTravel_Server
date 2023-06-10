package com.example.demo.src.post.repository;

import com.example.demo.src.post.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<String> findCategoryNameOrderByCategoryName();
    String findCategoryNameByCategoryIdx(Long categoryIdx);
}

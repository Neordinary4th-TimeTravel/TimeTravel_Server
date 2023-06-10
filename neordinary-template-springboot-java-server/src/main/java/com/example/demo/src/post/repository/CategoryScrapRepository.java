package com.example.demo.src.post.repository;

import com.example.demo.src.member.entity.Member;
import com.example.demo.src.post.entity.Category;
import com.example.demo.src.post.entity.CategoryScrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryScrapRepository extends JpaRepository<CategoryScrap, Long> {
}

package com.example.demo.src.Open;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.post.entity.Category;
import com.example.demo.src.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryOpenRepositroy  extends JpaRepository<Category, Long> {
    Category getByCategoryNameAndState(String categoryName, BaseEntity.State state);
}

package com.example.demo.src.post.dto;

import com.example.demo.src.post.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostCategoryResDto {
    @Schema(name = "categoryList", example = "")
    private List<Category> categoryList;
}

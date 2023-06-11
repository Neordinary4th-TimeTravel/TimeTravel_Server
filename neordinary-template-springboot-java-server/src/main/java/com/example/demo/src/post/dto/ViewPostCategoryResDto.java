package com.example.demo.src.post.dto;

import com.example.demo.src.post.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ViewPostCategoryResDto {
    @Schema(name = "categoryList", example = "[노래, 영화, 만화]", description = "카테고리 목록")
    private List<String> categoryList;
}

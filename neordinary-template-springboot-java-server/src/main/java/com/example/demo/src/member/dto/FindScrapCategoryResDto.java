package com.example.demo.src.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FindScrapCategoryResDto {
    @Schema(name = "categoryNameList", example = "[노래, 영화, 만화]", description = "사용자가 좋아요 누른 카테고리 목록")
    private List<String> categoryNameList;
}

package com.example.demo.src.post.dto;

import com.example.demo.src.member.entity.Member;
import com.example.demo.src.post.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScrapPostCategoryReqDto {

    @Schema(name = "memberIdx", example = "1", description = "멤버 인덱스")
    private Member memberIdx;

    @Schema(name = "categoryIdx", example = "1", description = "카테고리 인덱스")
    private Category CategoryIdx;
}

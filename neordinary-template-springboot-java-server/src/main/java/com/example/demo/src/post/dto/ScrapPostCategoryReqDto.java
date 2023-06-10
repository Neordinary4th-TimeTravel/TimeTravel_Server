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

    @Schema(name = "memberIdx", example = "1")
    private Member memberIdx;

    @Schema(name = "categoryIdx", example = "1")
    private Category CategoryIdx;
}

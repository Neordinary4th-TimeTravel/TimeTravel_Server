package com.example.demo.src.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ToggleCapsuleLikeReqDto {
    @Schema(name = "memberIdx", example = "1", description = "유저 아이디", required = true)
    private Long memberIdx;
    @Schema(name = "postIdx", example = "1", description = "게시글 아이디", required = true)
    private Long postIdx;
}
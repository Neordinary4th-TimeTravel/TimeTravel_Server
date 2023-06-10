package com.example.demo.src.Open.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostCapsuleResDto {
    @Schema(name = "categoryIdx", example = "1", description = "카테고리 아이디", required = true)
    private Long categoryIdx;
    @Schema(name = "memberIdx", example = "1", description = "유저 아이디", required = true)
    private Long memberIdx;
    @Schema(name = "postTitle", example = "미래의 나에게", description = "게시글 제목", required = true)
    private String postTitle;
    @Schema(name = "postYear", example = "2000", description = "타겟 년도", required = true)
    private Integer postYear;
    @Schema(name = "postText", example = "안녕 잘지내?", description = "게시글", required = true)
    private String postText;
    @Schema(name = "postSong", example = "Y-프리스타일", description = "추억의 노래", required = true)
    private String postSong;
    @Schema(name = "postRelease", example = "2024-10-05T14:48:00.000Z", description = "공개 시간", required = true)
    private LocalDateTime postRelease;
    @Schema(name = "postPublic", example = "1", description = "1: 공개, 0:비공개", required = true)
    private boolean postPublic;
}

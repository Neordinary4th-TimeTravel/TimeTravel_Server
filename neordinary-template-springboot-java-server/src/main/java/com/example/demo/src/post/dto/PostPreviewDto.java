package com.example.demo.src.post.dto;

import com.example.demo.src.member.entity.Member;
import com.example.demo.src.post.entity.Category;
import com.example.demo.src.post.entity.Post;
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
public class PostPreviewDto {

    @Schema(name = "postIdx", example = "1", description = "캡슐 인덱스")
    private Long postIdx;

    @Schema(name = "categotyIdx", example = "1", description = "카테고리 인덱스")
    private Category categoryIdx;

    @Schema(name = "memberIdx", example = "1", description = "회원 인덱스")
    private Member memberIdx;

    @Schema(name = "postTitle", example = "캡슐 제목이다아", description = "캡슐 제목")
    private String postTitle;

    @Schema(name = "postYear", example = "1990", description = "캡슐 연관 년대")
    private Integer postYear;

    @Schema(name = "postText", example = "캡슐 내용이다앙", description = "캡슐 내용")
    private String postText;

    @Schema(name = "postSong", example = "자이언티-노래", description = "해당 년대 노래")
    private String postSong;

    @Schema(name = "postRelease", example = "2023-06-11T09:57", description = "캡슐 해제시간")
    private LocalDateTime postRelease;

    @Schema(name = "postPublic", example = "1", description = "캡슐 공개 여부")
    private Boolean postPublic;

    public PostPreviewDto(Post allByPostIdx) {
        this.postIdx = allByPostIdx.getPostIdx();
        this.categoryIdx = allByPostIdx.getCategoryIdx();
        this.memberIdx = allByPostIdx.getMemberIdx();
        this.postTitle = allByPostIdx.getPostTitle();
        this.postYear = allByPostIdx.getPostYear();
        this.postText = allByPostIdx.getPostText();
        this.postSong = allByPostIdx.getPostSong();
        this.postRelease = allByPostIdx.getPostRelease();
        this.postPublic = allByPostIdx.getPostPublic();
    }
}

package com.example.demo.src.post.dto;

import com.example.demo.src.member.entity.Member;
import com.example.demo.src.post.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ViewPostResDto {

    @Schema(name = "categoryName", example = "영화", description = "카테고리명")
    private String categoryName;

    @Schema(name = "memberNickname", example = "바보", description = "작성자 닉네임")
    private String memberNickname;

    @Schema(name = "postTitle", example = "제목이다~", description = "캡슐 제목")
    private String postTitle;

    @Schema(name = "postYear", example = "1990", description = "캡슐 연관 년대")
    private Integer postYear;

    @Schema(name = "postText", example = "내용이지롱~", description = "캡슐 내용")
    private String postText;

    @Schema(name = "postSong", example = "사랑비", description = "캡슐 노래")
    private String postSong;

    @Schema(name = "postRelease", example = "2023-06-11T03:40", description = "캡슐 해제 시간")
    private LocalDateTime postRelease;

    @Schema(name = "postPublic", example = "1", description = "캡슐 공개 여부")
    private Boolean postPublic;

    @Builder
    public ViewPostResDto(String categoryName, String memberNickname, String postTitle, Integer postYear, String postText, String postSong, LocalDateTime postRelease, Boolean postPublic) {
        this.categoryName = categoryName;
        this.memberNickname = memberNickname;
        this.postTitle = postTitle;
        this.postYear = postYear;
        this.postText = postText;
        this.postSong = postSong;
        this.postRelease = postRelease;
        this.postPublic = postPublic;
    }
}

package com.example.demo.src.post.dto;

import com.example.demo.src.member.entity.Member;
import com.example.demo.src.post.entity.Category;
import com.example.demo.src.post.entity.Post;
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

    private Long postIdx;

    private Category categoryIdx;

    private Member memberIdx;

    private String postTitle;

    private Integer postYear;

    private String postText;

    private String postSong;

    private LocalDateTime postRelease;

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

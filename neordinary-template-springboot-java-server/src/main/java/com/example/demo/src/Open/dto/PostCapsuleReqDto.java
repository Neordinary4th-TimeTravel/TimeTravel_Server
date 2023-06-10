package com.example.demo.src.Open.dto;

import com.example.demo.src.post.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostCapsuleReqDto {
    private Category categoryIdx;
    private Long memberIdx;
    private Integer postYear;
    private String postText;
    private LocalDateTime postRelease;
    private boolean postPublic;
}

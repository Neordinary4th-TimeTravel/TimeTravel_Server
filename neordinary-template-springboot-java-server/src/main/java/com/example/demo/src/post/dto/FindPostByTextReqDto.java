package com.example.demo.src.post.dto;

import com.example.demo.src.member.entity.Member;
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
public class FindPostByTextReqDto {

    @Schema(name = "postText", example = "포켓몬")
    private String postText;

    @Schema(name = "scrollSize", example = "7")
    private int scrollSize;

    @Schema(name = "createdAt", example = "2023-06-11T01:05")
    private LocalDateTime createdAt;
}

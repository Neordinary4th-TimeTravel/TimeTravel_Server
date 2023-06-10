package com.example.demo.src.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CapDto {

    @Schema(name = "creationDateTime", example = "DATETIME", required = true, description = "생성일")
    private String creationDateTime;
    @Schema(name = "releaseDateTime", example = "DATETIME", required = true, description = "해제일")
    private String releaseDateTime;
    @Schema(name = "capsuleTitle", example = "제목", required = true, description = "캡슐 제목")
    private String capsuleTitle;
}

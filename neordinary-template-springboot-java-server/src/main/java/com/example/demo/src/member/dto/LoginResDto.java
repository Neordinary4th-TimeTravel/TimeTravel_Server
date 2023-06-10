package com.example.demo.src.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class LoginResDto {

    @Schema(name = "nickname", example = "Rookie", description = "유저 닉네임(아이디)")
    private String nickname;
}

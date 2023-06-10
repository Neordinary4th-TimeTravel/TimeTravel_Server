package com.example.demo.src.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginResDto {

    @Schema(name = "memberIdx", example = "1", description = "유저 인덱스")
    private Long memberIdx;
    @Schema(name = "nickname", example = "Rookie", description = "유저 닉네임(아이디)")
    private String nickname;
    @Schema(name = "accessToken", example = "Token", description = "엑섿스 토큰")
    private String accessToken;
    @Schema(name = "refreshToken", example = "Token", description = "리프래시 토큰")
    private String refreshToken;
}

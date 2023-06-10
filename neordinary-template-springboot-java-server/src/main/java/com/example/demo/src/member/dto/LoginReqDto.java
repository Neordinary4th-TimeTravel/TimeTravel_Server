package com.example.demo.src.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginReqDto {
    @Schema(name = "email", example = "example@example.com", description = "유저 이메일", required = true)
    private String email;
    @Schema(name = "password", example = "123456", description = "비밀번호", required = true)
    private String password;
}

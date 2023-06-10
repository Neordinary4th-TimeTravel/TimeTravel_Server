package com.example.demo.src.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema
@AllArgsConstructor
@NoArgsConstructor
public class PatchNicknameResDto {
    @Schema(name = "modifiedNickname", example = "Rookie2", required = true, description = "변경한 닉네임(아이디)")
    private String modifiedNickname;
}

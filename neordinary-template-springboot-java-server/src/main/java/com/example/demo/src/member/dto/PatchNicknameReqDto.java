package com.example.demo.src.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema
@AllArgsConstructor
@NoArgsConstructor
public class PatchNicknameReqDto {

    @Schema(name = "memberIdx", example = "1", required = true, description = "유저 인덱스")
    private Long memberIdx;
    @Schema(name = "targertNickname", example = "Rookie2", required = true, description = "변경할 닉네임(아이디)")
    private String targertNickname;
}

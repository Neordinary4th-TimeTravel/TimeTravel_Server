package com.example.demo.src.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EmailCheckResDto {
    @Schema(name = "checkResult", example = "false", required = true, description = "false == 중복 없음")
    private boolean checkResult;
}

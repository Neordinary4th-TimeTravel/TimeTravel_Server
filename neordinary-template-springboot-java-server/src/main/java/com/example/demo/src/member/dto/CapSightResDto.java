package com.example.demo.src.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

public class CapSightResDto {

    @Schema(name = "privateList", example = "List", required = true, description = "비공개 리스트")
    List<CapDto> privateList;
    @Schema(name = "publicList", example = "List", required = true, description = "공개 리스트")
    List<CapDto> publicList;

    @Builder
    public CapSightResDto(List<CapDto> privateList, List<CapDto> publicList) {
        this.privateList = privateList;
        this.publicList = publicList;
    }
}

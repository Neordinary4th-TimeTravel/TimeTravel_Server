package com.example.demo.src.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class CapSightResDto {

    @Schema(name = "privateList", required = true, description = "비공개 리스트")
    List<CapDto> privateList;
    @Schema(name = "publicList", required = true, description = "공개 리스트")
    List<CapDto> publicList;

    @Builder
    public CapSightResDto(List<CapDto> privateList, List<CapDto> publicList) {
        this.privateList = privateList;
        this.publicList = publicList;
    }
}

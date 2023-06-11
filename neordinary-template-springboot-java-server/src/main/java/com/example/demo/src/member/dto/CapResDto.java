package com.example.demo.src.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CapResDto {

    @Schema(name = "closedList", required = true, description = "열리기 전 리스트")
    private List<CapDto> closedList;
    @Schema(name = "openedList", required = true, description = "열린 리스트")
    private List<CapDto> openedList;

    @Builder
    public CapResDto(List<CapDto> closedList, List<CapDto> openedList) {
        this.closedList = closedList;
        this.openedList = openedList;
    }
}

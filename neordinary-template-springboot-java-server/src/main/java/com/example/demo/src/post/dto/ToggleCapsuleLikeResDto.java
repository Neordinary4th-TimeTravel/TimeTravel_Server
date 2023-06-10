package com.example.demo.src.post.dto;

import com.example.demo.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ToggleCapsuleLikeResDto {
    @Schema(name = "state", example = "ACTIVE", description = "활성화", required = true)
    private BaseEntity.State state;
}

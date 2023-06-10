package com.example.demo.src.post.dto;

import com.example.demo.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ToggleCapsuleLikeResDto {
    private BaseEntity.State state;
}

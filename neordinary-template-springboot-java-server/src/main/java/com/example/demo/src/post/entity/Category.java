package com.example.demo.src.post.entity;


import com.example.demo.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity // 필수, Class 를 Database Table화 해주는 것이다
@Table(name = "CATEGORY") // Table 이름을 명시해주지 않으면 class 이름을 Table 이름으로 대체한다.
public class Category extends BaseEntity {

    @Id // PK를 의미하는 어노테이션
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryIdx;

    @Column(nullable = false, length = 45)
    private String categoryName;

    @Builder
    public Category(Long categoryIdx, String categoryName) {
        this.categoryIdx = categoryIdx;
        this.categoryName = categoryName;
    }
}

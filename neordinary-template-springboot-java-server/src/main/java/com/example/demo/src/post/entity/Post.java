package com.example.demo.src.post.entity;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity // 필수, Class 를 Database Table화 해주는 것이다
@Table(name = "POST") // Table 이름을 명시해주지 않으면 class 이름을 Table 이름으로 대체한다.
public class Post extends BaseEntity {

    @Id // PK를 의미하는 어노테이션
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postIdx;

    @ManyToOne
    @JoinColumn(name = "categoryIdx")
    private Category categoryIdx;

    @ManyToOne
    @JoinColumn(name = "memberIdx")
    private Member memberIdx;

    @Column(nullable = false)
    private String postTitle;

    @Column(nullable = false)
    private Integer postYear;

    @Column(nullable = false)
    private String postText;

    @Column(nullable = false)
    private LocalDateTime postRelease;

    //공개 : 1 비공개 : 0
    @Column(nullable = false)
    private Boolean postPublic;

    @Builder
    public Post(Long postIdx, Category categoryIdx, Member memberIdx, String postTitle, Integer postYear, String postText, LocalDateTime postRelease, Boolean postPublic) {
        this.postIdx = postIdx;
        this.categoryIdx = categoryIdx;
        this.memberIdx = memberIdx;
        this.postTitle = postTitle;
        this.postYear = postYear;
        this.postText = postText;
        this.postRelease = postRelease;
        this.postPublic = postPublic;
    }

    @Builder
    public Post(Category categoryIdx, Member memberIdx,String postTitle ,Integer postYear, String postText, LocalDateTime postRelease, Boolean postPublic) {
        this.categoryIdx = categoryIdx;
        this.memberIdx = memberIdx;
        this.postTitle = postTitle;
        this.postYear = postYear;
        this.postText = postText;
        this.postRelease = postRelease;
        this.postPublic = postPublic;
    }
}

package com.example.demo.src.post.entity;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.member.entity.Member;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity // 필수, Class 를 Database Table화 해주는 것이다
@Table(name = "COMMENT") // Table 이름을 명시해주지 않으면 class 이름을 Table 이름으로 대체한다.
public class Comment extends BaseEntity {

    @Id // PK를 의미하는 어노테이션
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentIdx;

    @ManyToOne
    @JoinColumn(name = "postIdx")
    private Post postIdx;

    @ManyToOne
    @JoinColumn(name = "memberIdx")
    private Member memberIdx;

    @Column(nullable = false)
    private String commentText;

    @Builder
    public Comment(Long commentIdx, Post postIdx, Member memberIdx, String commentText) {
        this.commentIdx = commentIdx;
        this.postIdx = postIdx;
        this.memberIdx = memberIdx;
        this.commentText = commentText;
    }
}

package com.example.demo.src.post.entity;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.member.entity.Member;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity // 필수, Class 를 Database Table화 해주는 것이다
@Table(name = "POSTLIKE") // Table 이름을 명시해주지 않으면 class 이름을 Table 이름으로 대체한다.
public class PostLike extends BaseEntity {

    @Id // PK를 의미하는 어노테이션
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeIdx;

    @ManyToOne
    @JoinColumn(name = "memberIdx")
    private Member memberIdx;

    @ManyToOne
    @JoinColumn(name = "postIdx")
    private Post postIdx;

    @Builder
    public PostLike(Long likeIdx, Member memberIdx, Post postIdx) {
        this.likeIdx = likeIdx;
        this.memberIdx = memberIdx;
        this.postIdx = postIdx;
    }
}

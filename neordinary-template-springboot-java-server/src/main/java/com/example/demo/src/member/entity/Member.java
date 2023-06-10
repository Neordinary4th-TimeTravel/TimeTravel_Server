package com.example.demo.src.member.entity;

import com.example.demo.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity // 필수, Class 를 Database Table화 해주는 것이다
@Table(name = "MEMBER") // Table 이름을 명시해주지 않으면 class 이름을 Table 이름으로 대체한다.
public class Member extends BaseEntity {

    @Id // PK를 의미하는 어노테이션
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberIdx;

    @Column(nullable = false, length = 45)
    private String memberEmail;

    @Column(nullable = false, length = 500)
    private String memberPwd;

    @Column(nullable = false, length = 45)
    private String nickname;

    @Column(nullable = false)
    private Boolean gender;

    @Column(nullable = false)
    private Integer age;

    @Builder
    public Member(Long memberIdx, String memberEmail, String memberPwd, String nickname, Boolean gender, Integer age) {
        this.memberIdx = memberIdx;
        this.memberEmail = memberEmail;
        this.memberPwd = memberPwd;
        this.nickname = nickname;
        this.gender = gender;
        this.age = age;
    }
}

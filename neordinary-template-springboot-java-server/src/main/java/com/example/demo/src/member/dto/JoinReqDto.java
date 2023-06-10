package com.example.demo.src.member.dto;

import com.example.demo.src.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JoinReqDto {
    @Schema(name = "email", example = "example@example.com", required = true, description = "유저 이메일")
    private String email;
    @Schema(name = "password", example = "123456", required = true, description = "비밀번호")
    private String password;
    @Schema(name = "nickname", example = "Rookie", required = true, description = "닉네임(아이디)")
    private String nickname;
    @Schema(name = "gender", example = "0", required = true, description = "남자면 0 여자면 1")
    private Integer gender;
    @Schema(name = "age", example = "0", required = true, description = "0(10~14), 1(15~19), 2(20~24), 3(25~30), 4(31~40), 5(41~)")
    private Integer age;

    public Member toEntity(){
        return Member.builder()
                .memberEmail(this.email)
                .memberPwd(this.password)
                .nickname(this.nickname)
                .gender(this.gender == 0)
                .age(this.age)
                .build();
    }

    //비밀번호 암호화 Setter
    public void encryptPassword(String encrypted){
        this.password = encrypted;
    }
}

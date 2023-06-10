package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//클라이언트로 보낼 jwtToken, accessToken등이 담긴 객체
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetSocialOAuthRes {

    private String jwtToken;
    private Long userId;
    private String accessToken;
    private String tokenType;
}

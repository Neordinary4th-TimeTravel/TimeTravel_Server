package com.example.demo.src.member;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponse;
import com.example.demo.src.member.dto.JoinReqDto;
import com.example.demo.src.member.dto.JoinResDto;
import com.example.demo.utils.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/member/")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final JwtService jwtService;

    /**
     * 회원가입
     * */
    @Tag(name = "회원가입 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.")
    })
    @Operation(summary = "회원가입", description = "유저 회원가입을 위한 API")
    @PostMapping("/join")
    public BaseResponse<JoinResDto> memberJoin(@RequestBody JoinReqDto req){

        try{
            return new BaseResponse<>(memberService.memberJoin(req));
        }catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 로그인
     * */

    /**
     * 닉네임 변경
     * */

    /**
     * 내가 작성한 캡슐 목록
     * */

    /**
     * 내가 스크랩을 한 캡슐 목록
     * */

    /**
     * 내가 태그 당한 캡슐 목록
     * */
}

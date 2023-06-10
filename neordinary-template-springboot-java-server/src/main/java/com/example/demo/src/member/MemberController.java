package com.example.demo.src.member;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponse;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.src.member.dto.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.ValidationRegex;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/member/")
@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.")})
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final JwtService jwtService;

    /**
     * 회원가입
     * */
    @Tag(name = "회원가입 API")
    @Operation(summary = "회원가입", description = "유저 회원가입을 위한 API")
    @PostMapping("/join")
    public BaseResponse<JoinResDto> memberJoin(@RequestBody JoinReqDto req){

        try{
            return new BaseResponse<>(memberService.memberJoin(req));
        }catch (BaseException exception) {
            log.error(exception.getMessage());
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 로그인
     * */
    @Tag(name = "로그인 API")
    @ApiResponses(value = {

            @ApiResponse(responseCode = "404", description = "없는 아이디거나 비밀번호가 틀렸습니다."
                    , content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "500", description = "데이터베이스 연결에 실패하였습니다."
                    , content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @Operation(summary = "로그인", description = "유저 로그인을 위한 API")
    @PostMapping("/login")
    public BaseResponse<LoginResDto> memberLogin(@RequestBody LoginReqDto req){
        try{
            return new BaseResponse<>(memberService.memberLogin(req));
        }catch (BaseException exception) {
            log.error(exception.getMessage());
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * 이메일 중복 체크
     * */
    @Tag(name = "이메일 중복 체크 API")
    @ApiResponses(value = {

            @ApiResponse(responseCode = "500", description = "이메일 형식을 확인해주세요."
                    , content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "500", description = "중복된 이메일입니다."
                    , content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "500", description = "데이터베이스 연결에 실패하였습니다."
                    , content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @Operation(summary = "로그인", description = "이메일 중복 체크를 위한 API")
    @PostMapping("/check-email")
    public BaseResponse<EmailCheckResDto> checkEmail(@RequestBody EmailCheckReqDto req) {

        try{
            if(!ValidationRegex.isRegexEmail(req.getEmail())) throw new BaseException(BaseResponseStatus.POST_USERS_INVALID_EMAIL);

            return new BaseResponse<>(memberService.chechEmail(req));
        }catch (BaseException exception) {
            log.error(exception.getMessage());
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * 닉네임 변경
     * */
    @Tag(name = "닉네임 변경 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "일치하는 유저가 없습니다."
                    , content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "500", description = "데이터베이스 연결에 실패하였습니다."
                    , content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @Operation(summary = "로그인", description = "닉네임 변경을 위한 API")
    @PatchMapping("/member/mod-nickname")
    public BaseResponse<PatchNicknameResDto> patchNickname(@RequestBody PatchNicknameReqDto req){

        try{
            return new BaseResponse<>(memberService.pathNickname(req));
        }catch (BaseException exception){
            log.error(exception.getMessage());
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * 내가 작성한 캡슐 목록
     * */
    @Tag(name = "내가 작성한 캡슐 목록 API")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "일치하는 유저가 없습니다."
                    , content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "404", description = "캡슐을 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "500", description = "데이터베이스 연결에 실패하였습니다."
                    , content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @Operation(summary = "내가 작성한 캡슐 목록", description = "내가 작성한 캡슐 목록 조회를 위한 API")
    @GetMapping("/capsules/{memberIdx}")
    public BaseResponse<CapResDto> getWrittenCap(@PathVariable("memberIdx") Long memberIdx){

        try{

            return new BaseResponse<>(memberService.getWrittenCap(memberIdx));
        }catch (BaseException exception) {
            log.error(exception.getMessage());
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * 내가 댓글을 단 캡슐 목록
     * */
    @Tag(name = "내가 댓글을 단 캡슐 목록 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "일치하는 유저가 없습니다."
                    , content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "404", description = "작성한 댓글이 존재하지 않습니다."
                    , content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "500", description = "데이터베이스 연결에 실패하였습니다."
                    , content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @Operation(summary = "내가 댓글을 단 캡슐 목록", description = "내가 댓글을 단 캡슐 목록 조회를 위한 API")
    @GetMapping("/capsules/comment/{memberIdx}")
    public BaseResponse<CapResDto> getCommentedPost(@PathVariable("memberIdx") Long memberIdx){
        try{

            return new BaseResponse<>(memberService.getCommentedPost(memberIdx));
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * 내가 좋아요를 한 캡슐 목록
     * */
    @Tag(name = "내가 좋아요한 캡슐 목록 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "일치하는 유저가 없습니다."
                    , content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "404", description = "좋아요한 캡슐을 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "500", description = "데이터베이스 연결에 실패하였습니다."
                    , content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @Operation(summary = "내가 좋아요한 캡슐 목록", description = "내가 좋아요한 캡슐 목록 조회를 위한 API")
    @GetMapping("/capsules/like/{memberIdx}")
    public BaseResponse<CapResDto> getLikePost(@PathVariable("memberIdx") Long memberIdx){
        try{

            return new BaseResponse<>(memberService.getLikePost(memberIdx));
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * 내가 태그 당한 캡슐 목록
     * */
    @Tag(name = "내가 태그 당한 캡슐 목록 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "일치하는 유저가 없습니다."
                    , content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "404", description = "태그한 캡슐을 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "500", description = "데이터베이스 연결에 실패하였습니다."
                    , content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @Operation(summary = "내가 태그 당한 캡슐 목록", description = "내가 태그 당한 캡슐 목록 조회를 위한 API")
    @GetMapping("/capsules/tag/{memberIdx}")
    public BaseResponse<CapResDto> getTagPost(@PathVariable("memberIdx") Long memberIdx) {
        try{

            return new BaseResponse<>(memberService.getTagPost(memberIdx));
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 내가 작성한 캡슐 공개/비공개 조회
     * */
    @Tag(name = "내가 작성한 캡슐 공개/비공개 목록 API")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "일치하는 유저가 없습니다."
                    , content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "404", description = "캡슐을 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "500", description = "데이터베이스 연결에 실패하였습니다."
                    , content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @Operation(summary = "내가 작성한 캡슐 공개/비공개 목록", description = "내가 작성한 캡슐 공개/비공개 목록 조회를 위한 API")
    @GetMapping("/capsules/sight/{memberIdx}")
    public BaseResponse<CapSightResDto> getSightPost(@PathVariable("memberIdx") Long memberIdx) {
        try{

            return new BaseResponse<>(memberService.getSightPost(memberIdx));
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 회원가입
     * */
    @Tag(name = "내가 스크랩(즐겨찾기)한 카테고리 목록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "일치하는 유저가 없습니다."
                    , content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "404", description = "캡슐을 찾을 수 없습니다."
                    , content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "500", description = "데이터베이스 연결에 실패하였습니다."
                    , content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @Operation(summary = "내가 스크랩(즐겨찾기)한 카테고리 목록", description = "내가 스크랩(즐겨찾기)한 카테고리 목록을 조회하기 위한 API")
    @GetMapping("/capsules/scrap/{memberIdx}")
    public BaseResponse<FindScrapCategoryResDto> findScrapCategory(@PathVariable("memberIdx") Long memberIdx){

        try{
            return new BaseResponse<>(memberService.findScrapCategory(memberIdx));
        }catch (BaseException exception) {
            log.error(exception.getMessage());
            return new BaseResponse<>(exception.getStatus());
        }
    }
}

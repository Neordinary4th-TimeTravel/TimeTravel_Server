package com.example.demo.src.post;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponse;
import com.example.demo.src.member.dto.JoinReqDto;
import com.example.demo.src.member.dto.JoinResDto;
import com.example.demo.src.post.dto.PostCategoryResDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "캡슐 API")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.")
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/capsules")
public class PostController {

    private final PostService postService;

    /**
     * 게시판 카테고리 목록 조회
     * */
    @Tag(name = "게시판 카테고리 목록 조회 API")
    @ApiResponses(value = {

    })
    @Operation(summary = "게시판 카테고리 목록 조회", description = "게시판 카테고리 목록을 조회하기 위한 API")
    @GetMapping("/category")
    public BaseResponse<PostCategoryResDto> viewPostCategory(){
        try{
            return new BaseResponse<>(postService.viewPostCategory());
        }catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}

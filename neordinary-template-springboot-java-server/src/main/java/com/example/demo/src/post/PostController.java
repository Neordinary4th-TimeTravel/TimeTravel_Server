package com.example.demo.src.post;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponse;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.src.member.entity.Member;
import com.example.demo.src.post.dto.*;
import com.example.demo.src.post.entity.Post;
import com.example.demo.src.post.entity.PostLike;
import com.example.demo.src.post.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Tag(name = "캡슐 API")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다."),
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
    @Operation(summary = "게시판 카테고리 목록 조회", description = "게시판 카테고리 목록을 조회하기 위한 API")
    @GetMapping("/category")
    public BaseResponse<ViewPostCategoryResDto> viewPostCategory(){
        try{
            return new BaseResponse<>(postService.viewPostCategory());
        }catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 게시판 카테고리 스크랩(즐겨찾기)
     * */
    @Tag(name = "게시판 카테고리 스크랩(즐겨찾기) API")
    @Operation(summary = "게시판 카테고리 스크랩(즐겨찾기)", description = "특정 게시판 카테고리 스크랩(즐겨찾기)을 위한 API")
    @PostMapping("/category/scrap")
    public BaseResponse<BaseResponseStatus> scrapPostCategory(@RequestBody ScrapPostCategoryReqDto req){
        try{
            return new BaseResponse<>(postService.scrapPostCategory(req));
        }catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 게시판 캡슐 검색(내용 기반)
     * */
    @Tag(name = "게시판 캡슐 검색(내용 기반) API")
    @Operation(summary = "게시판 캡슐 검색(내용 기반)", description = "게시판에서 특정 내용을 포함한 캡슐 검색을 위한 API")
    @GetMapping("/category/search/text")
    public BaseResponse<FindPostByTextResDto> findPostByText(@RequestParam(name = "post-text") String postText,
                                                             @RequestParam(name = "scroll-size") int scrollSize){
        try{
            return new BaseResponse<>(postService.findPostByText(postText, scrollSize));
        }catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 특정 카테고리의 캡슐 목록 조회
     * */
    @Tag(name = "특정 카테고리의 캡슐 목록 조회 API")
    @Operation(summary = "특정 카테고리의 캡슐 목록 조회", description = "특정 카테고리 게시판의 캡슐 목록 조회를 위한 API")
    @GetMapping("/category/search")
    public BaseResponse<FindPostByCategoryResDto> findPostByCategory(@RequestParam(name = "category-idx") Long categoryIdx,
                                                                     @RequestParam(name = "scroll-size") int scrollSize){
        try{
            return new BaseResponse<>(postService.findPostByCategory(categoryIdx, scrollSize));
        }catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 캡슐 내용 조회
     * */
    @Tag(name = "캡슐 내용 조회 API")
    @Operation(summary = "캡슐 내용 조회", description = "캡슐 내용 상세 조회를 위한 API")
    @GetMapping("/")
    public BaseResponse<ViewPostResDto> viewPost(@RequestParam(name = "post-idx") Long postIdx){
        try{
            return new BaseResponse<>(postService.viewPost(postIdx));
        }catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * 캡슐 - 캡슐 좋아요 API
     * @RequestBody 캡슐 좋아요에 필요한 DTO
     * @return
     */
    @Tag(name = "캡슐 좋아요 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.")
    })
    @Operation(summary = "캡슐 좋아요 API", description = "캡슐 등록하기 위한 api")
    @PostMapping("capsule")
    public BaseResponse<ToggleCapsuleLikeResDto> createCapsule(@RequestParam(value = "X-ACCESS-TOKEN",required = false) String token, @RequestBody ToggleCapsuleLikeReqDto toggleCapsuleLikeReqDto) {
        try{
            return new BaseResponse<>(postService.ToggleCapsuleLike(toggleCapsuleLikeReqDto));
        }catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


}

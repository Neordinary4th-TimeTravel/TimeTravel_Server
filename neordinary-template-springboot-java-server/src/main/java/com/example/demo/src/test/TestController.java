package com.example.demo.src.test;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponse;
import com.example.demo.src.test.model.PostCommentDto;
import com.example.demo.src.test.model.GetMemoDto;
import com.example.demo.src.test.model.MemoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.common.response.BaseResponseStatus.*;


@Tag(name = "test 도메인", description = "메모 API, 코멘트 API") // swagger 접속: http://localhost:9000/swagger-ui/index.html
@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {

    private final TestService testService;


    /**
     * 로그 테스트 API
     * [GET] /test/log
     * @return String
     */
    @ResponseBody
    @GetMapping("/log")
    public String logTest() {
        System.out.println("테스트");
        return "Success Test";
    }

    /**
     * 메모 생성 API
     * [POST] /test/memos
     * @return BaseResponse<String>
     */
    // Body
    @Operation(summary = "메모 생성", description = "문자열을 받아 메모를 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "POST_TEST_EXISTS_MEMO"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @ResponseBody
    @PostMapping("/memos")
    public BaseResponse<String> createMemo(@Validated @RequestBody MemoDto memoDto) {
        testService.createMemo(memoDto);
        return new BaseResponse<>("생성 성공!!");
    }


    /**
     * 메모 리스트 조회 API
     * [GET] /test/memos
     * @return BaseResponse<List<TestDto>>
     */
    //Query String
    @Operation(summary = "메모 리스트 조회", description = "저장된 메모 리스트를 조회합니다.")
    @ResponseBody
    @GetMapping("/memos")
    public BaseResponse<List<GetMemoDto>> getMemos(@RequestParam(required = true) int startPage) {
        List<GetMemoDto> getMemoDtoList = testService.getMemos(startPage);
        return new BaseResponse<>(getMemoDtoList);
    }


    /**
     * 메모 정보 변경 API
     * [PATCH] /test/memos/{memoId}
     * @return BaseResponse<String>
     */
    @Operation(summary = "메모 정보 변경", description = "입력받은 메모 id의 메모를 받아온 문자열 값으로 변경합니다.")
    @ResponseBody
    @PatchMapping("/memos/{memoId}")
    public BaseResponse<String> modifyMemo(@PathVariable("memoId") Long memoId, @Validated @RequestBody MemoDto memoDto) {
        testService.modifyMemo(memoId, memoDto);

        String result = "수정 성공!!";
        return new BaseResponse<>(result);

    }


    /**
     * 코멘트 생성 API
     * [POST] /test/comments
     * @return BaseResponse<String>
     */
    // Body
    @Operation(summary = "코멘트 생성", description = "입력 받은 메모에 받아온 문자열로 코멘트를 생성합니다.")
    @ResponseBody
    @PostMapping("/comments")
    public BaseResponse<String> createComment(@RequestBody PostCommentDto postCommentDto) {
        testService.createComment(postCommentDto);
        return new BaseResponse<>("성공");
    }
}

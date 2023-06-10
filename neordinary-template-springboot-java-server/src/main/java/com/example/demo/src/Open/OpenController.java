package com.example.demo.src.Open;

import com.example.demo.common.response.BaseResponse;
import com.example.demo.common.secret.Secret;
import com.example.demo.src.Open.dto.Message;
import com.example.demo.src.Open.dto.PostCapsuleReqDto;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.ValidationRegex;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/opens")
public class OpenController {
    private final OpenService openService;
    private final JwtService jwtService;
    private static final String ENDPOINT = "https://api.openai.com/v1/chat/completions";
    /**
     * OpenAI 키워드  - 키워드 입력 시 penAI 조회 API
     * @param keyword 글 작성 시 포함하고 싶은 내용
     * @param  format 글 작성 형태
     * @return OpenAI 결과 String
     */

    @Tag(name = "OPENAI API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.")
    })

    @Operation(summary = "openAI 요청", description = "글 작성을 위한 openAI API")
    @Parameters({
            @Parameter(name = "keyword", description = "글에 포함할 내용", example = "미래의 나에게"),
            @Parameter(name = "format", description = "chatGPT가 생성할 형식", example = "편지"),
    })
    @GetMapping("/result")
    public BaseResponse<String> getUserReservations(@RequestParam(required = true) String keyword,@RequestParam String format) {
        if(format.isEmpty()){
            format = "편지";
        }
        // 프로프트 생성
        String prompt = keyword + ", " + format + " 형식으로 50자 이내로 작성해줘.";

        // header 등록: Content-Type, Authorization(API KEY), Message
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + Secret.OPEN_API_SECRET_KEY);

        Map<String, Object> requestBody = new HashMap<>();

        // Message: model, role
        requestBody.put("model", "gpt-3.5-turbo");
        Message message = new Message("user", prompt);
        Message[] messages = {message};
        requestBody.put("messages", messages);
        try {
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> response = restTemplate.postForEntity(ENDPOINT, requestEntity, Map.class);
            // answer 추출
            String[] responses = response.getBody().get("choices").toString().split("role=assistant, content=|}, finish_reason=stop, index=0}");
            String answer = responses[1];
            return new BaseResponse<>(answer);
        }
        catch (Exception e){
            return new BaseResponse<>(e.getMessage());
        }
    }

    /**
     * 캡슐 - 캡슐 생성 API
     * @RequestBody 캡슐등록 시 필요한 DTO
     * @return String
     */
    @Tag(name = "캡슐 등록 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.")
    })
    @Operation(summary = "캡슐 등록 API", description = "캡슐 등록하기 위한 api")
    @Parameters({
            @Parameter(name = "categoryIdx", description = "카테고리 정보", example = "{}"),
            @Parameter(name = "memberIdx", description = "생성자 정보", example = "{}"),
            @Parameter(name = "postYear", description = "타겟 연도", example = "2000"),
            @Parameter(name = "postText", description = "미래에 보여줄 글", example = "안녕~~"),
            @Parameter(name = "postRelease", description = "글을 읽을 수 있는 날짜", example = "2024/03/12"),
            @Parameter(name = "postPublic", description = "공개(1)/비공개(0)", example = "1"),
    })
    @PostMapping("capsule")
    public BaseResponse<String> createCapsule(@RequestBody PostCapsuleReqDto postCapsuleReqDto) {
        ValidationRegex.isRegexTime(postCapsuleReqDto.getPostRelease().toString());
        try {
            Long capsuleIdx = openService.createCapsule(postCapsuleReqDto);
            return new BaseResponse<>("캡슐등록 성공");
        }
        catch (Exception e){
            return  new BaseResponse<>(e.getMessage());
        }
    }
}

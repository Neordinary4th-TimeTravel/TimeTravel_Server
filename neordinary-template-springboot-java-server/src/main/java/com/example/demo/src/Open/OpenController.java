package com.example.demo.src.Open;

import com.example.demo.common.response.BaseResponse;
import com.example.demo.src.Open.dto.Message;
import com.example.demo.src.Open.dto.PostCapsuleReqDto;
import com.example.demo.utils.JwtService;
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
    private String API_KEY = "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";
    private static final String ENDPOINT = "https://api.openai.com/v1/chat/completions";
    /**
     * OpenAI 키워드  - 키워드 입력 시 penAI 조회 API
     * @param
     * @return OpenAI 결과 String
     */
    @GetMapping("/result")
    public BaseResponse<String> getUserReservations(@RequestParam String keyword, String format) {

        String prompt = keyword;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        Message message = new Message("user", prompt);
        Message[] messages = {message};
        requestBody.put("messages", messages);
        try {
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> response = restTemplate.postForEntity(ENDPOINT, requestEntity, Map.class);
            String[] responses = response.getBody().get("choices").toString().split("role=assistant, content=|}, finish_reason=stop, index=0}");
            String answer = responses[1];
            return new BaseResponse<>(answer);
        }
        catch (Exception e){
            return new BaseResponse<>("error");
        }
    }

   
}

package com.example.demo.src.post;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "캡슐 API")
@ApiResponses({
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
})
@RestController
@RequiredArgsConstructor
@RequestMapping("/capsules")
public class PostController {
}

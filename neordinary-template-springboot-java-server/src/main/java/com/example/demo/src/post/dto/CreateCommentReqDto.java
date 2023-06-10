package com.example.demo.src.post.dto;

import com.example.demo.src.member.entity.Member;
import com.example.demo.src.post.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentReqDto {

    @Schema(name = "postIdx", example = "1", description = "댓글 작성 대상 게시글")
    private Post postIdx;
    @Schema(name = "memberIdx", example = "1", description = "유저 아이디")
    private Member memberIdx;
    @Schema(name = "commentText", example = "이게 댓글이다!!", description = "댓글 내용")
    private String commentText;
}

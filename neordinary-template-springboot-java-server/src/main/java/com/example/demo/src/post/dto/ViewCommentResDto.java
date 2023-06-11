package com.example.demo.src.post.dto;

import com.example.demo.src.member.entity.Member;
import com.example.demo.src.post.entity.Comment;
import com.example.demo.src.post.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ViewCommentResDto {

    @Schema(name = "commentIdx", example = "1", description = "댓글 인덱스")
    private Long commentIdx;
    @Schema(name = "postIdx", example = "1", description = "캡슐 인덱스")
    private Post postIdx;
    @Schema(name = "memberIdx", example = "1", description = "회원 인덱스")
    private Member memberIdx;
    @Schema(name = "commentText", example = "댓글 내용인걸", description = "댓글 내용")
    private String commentText;

    @Builder
    public ViewCommentResDto(Long commentIdx, Post postIdx, Member memberIdx, String commentText) {
        this.commentIdx = commentIdx;
        this.postIdx = postIdx;
        this.memberIdx = memberIdx;
        this.commentText = commentText;
    }
}

package com.example.demo.src.test.model;

import com.example.demo.src.test.entity.Comment;
import com.example.demo.src.test.entity.Memo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetMemoDto {
    private Long id;
    private String memo;
    private List<String> commentList = new ArrayList<>();

    public GetMemoDto(Memo memo) {
        this.id = memo.getId();
        this.memo = memo.getMemo();
        for(Comment comment : memo.getCommentList()) {
            this.commentList.add(comment.getComment());
        }
    }
}

package com.example.demo.src.post.dto;

import com.example.demo.common.scroll.ScrollPaginationCollection;
import com.example.demo.src.post.entity.Post;
import com.example.demo.src.post.repository.PostRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class FindPostByCategoryResDto {

    private static final long LAST_CURSOR = -1L;

    @Schema(name = "postTitleList", example = "[제목1, 제목2, 제목3]", description = "캡슐 목록(제목)")
    private List<String> postTitleList = new ArrayList<>();
    @Schema(name = "nextCursor", example = "4", description = "다음 커서")
    private long nextCursor;

    public FindPostByCategoryResDto(List<String> postTitleList, long nextCursor) {
        this.postTitleList = postTitleList;
        this.nextCursor = nextCursor;
    }

    public static FindPostByCategoryResDto of(ScrollPaginationCollection<Post> postScroll, PostRepository postRepository) {
        if (postScroll.isLastScroll()) {
            return FindPostByCategoryResDto.newLastScroll(postScroll.getCurrentScrollItems(), postRepository);
        }
        return FindPostByCategoryResDto.newScrollHasNext(postScroll.getCurrentScrollItems(), postRepository, postScroll.getNextCursor().getPostIdx());
    }

    private static FindPostByCategoryResDto newLastScroll(List<Post> postScroll, PostRepository postRepository) {
        return newScrollHasNext(postScroll, postRepository, LAST_CURSOR);
    }

    private static FindPostByCategoryResDto newScrollHasNext(List<Post> postScroll, PostRepository postRepository, long nextCursor) {
        return new FindPostByCategoryResDto(getPostTitle(postScroll, postRepository), nextCursor);
    }

    private static List<String> getPostTitle(List<Post> postScroll, PostRepository postRepository) {
        return postScroll.stream()
                .map(post -> postRepository.findPostTitleByPostIdx(post.getPostIdx()))
                .collect(Collectors.toList());
    }

}


package com.example.demo.src.post;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.common.scroll.ScrollPaginationCollection;
import com.example.demo.src.member.MemberRepository;
import com.example.demo.src.post.dto.*;
import com.example.demo.src.post.entity.CategoryScrap;
import com.example.demo.src.post.entity.Post;
import com.example.demo.src.post.repository.CategoryRepository;
import com.example.demo.src.post.repository.CategoryScrapRepository;
import com.example.demo.src.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryScrapRepository categoryScrapRepository;

    public ViewPostCategoryResDto viewPostCategory() throws BaseException {
        try{
            return new ViewPostCategoryResDto(categoryRepository.findCategoryNameOrderByCategoryName());
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public BaseResponseStatus scrapPostCategory(ScrapPostCategoryReqDto req) throws BaseException {
        try{
            CategoryScrap scrap = CategoryScrap.builder()
                    .categoryIdx(req.getCategoryIdx())
                    .memberIdx(req.getMemberIdx())
                    .build();
            categoryScrapRepository.save(scrap);
            return BaseResponseStatus.SUCCESS;
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public FindPostByTextResDto findPostByText(String postText, int scrollSize, LocalDateTime createdAt) throws BaseException {
        try{
            Pageable pageRequest = PageRequest.of(0, scrollSize + 1);
            Page<Post> page = postRepository.findAllByPostTextOrderByCreatedAtDesc(postText, createdAt, pageRequest);
            List<Post> postTitleList = page.getContent();

            ScrollPaginationCollection<Post> postCursor = ScrollPaginationCollection.of(postTitleList, scrollSize);

            return FindPostByTextResDto.of(postCursor, postRepository);
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public FindPostByCategoryResDto findPostByCategory(Long categoryIdx, int scrollSize, LocalDateTime createdAt) {
        try{
            Pageable pageRequest = PageRequest.of(0, scrollSize + 1);
            Page<Post> page = postRepository.findAllByCategoryIdxOrderByCreatedAtDesc(categoryIdx, createdAt, pageRequest);
            List<Post> postTitleList = page.getContent();

            ScrollPaginationCollection<Post> postCursor = ScrollPaginationCollection.of(postTitleList, scrollSize);

            return FindPostByCategoryResDto.of(postCursor, postRepository);
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public ViewPostResDto viewPost(Long postIdx) {
        try{
            Post post = postRepository.findAllByPostIdx(postIdx);
            ViewPostResDto res = ViewPostResDto.builder()
                    .memberNickname(memberRepository.findNicknameByMemberIdx(post.getMemberIdx().getMemberIdx()))
                    .categoryName(categoryRepository.findCategoryNameByCategoryIdx(post.getCategoryIdx().getCategoryIdx()))
                    .postYear(post.getPostYear())
                    .postTitle(post.getPostTitle())
                    .postText(post.getPostText())
                    .postSong(post.getPostSong())
                    .postRelease(post.getPostRelease())
                    .postPublic(post.getPostPublic())
                    .build();
            return res;
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}

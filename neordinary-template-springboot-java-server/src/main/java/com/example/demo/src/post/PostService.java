package com.example.demo.src.post;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.common.scroll.ScrollPaginationCollection;
import com.example.demo.src.post.dto.FindPostByTextReqDto;
import com.example.demo.src.post.dto.FindPostByTextResDto;
import com.example.demo.src.post.dto.ScrapPostCategoryReqDto;
import com.example.demo.src.post.dto.ViewPostCategoryResDto;
import com.example.demo.src.post.entity.CategoryScrap;
import com.example.demo.src.post.entity.Post;
import com.example.demo.src.post.repository.CategoryRepository;
import com.example.demo.src.post.repository.CategoryScrapRepository;
import com.example.demo.src.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional
public class PostService {

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

    public FindPostByTextResDto findPostByText(FindPostByTextReqDto req) throws BaseException {
        try{
            PageRequest pageRequest = PageRequest.of(0, req.getScrollSize() + 1);
            Page<Post> page = postRepository.findAllByPostTextOrderByCreatedAtDesc(req.getPostText(), req.getCreatedAt(), pageRequest);
            List<Post> postTitleList = page.getContent();

            ScrollPaginationCollection<Post> postCursor = ScrollPaginationCollection.of(postTitleList, req.getScrollSize());
            FindPostByTextResDto res = FindPostByTextResDto.of(postCursor, postRepository);

            return res;
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}

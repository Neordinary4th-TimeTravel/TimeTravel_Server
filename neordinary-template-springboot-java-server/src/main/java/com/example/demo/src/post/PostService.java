package com.example.demo.src.post;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.common.scroll.ScrollPaginationCollection;
import com.example.demo.src.member.entity.Member;
import com.example.demo.src.post.dto.*;
import com.example.demo.src.post.entity.CategoryScrap;
import com.example.demo.src.post.entity.Post;
import com.example.demo.src.post.entity.PostLike;
import com.example.demo.src.post.repository.*;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryScrapRepository categoryScrapRepository;
    private final PostLikeRepository postLikeRepository;
    private final MemberRepository memberRepository;

    private final JwtService jwtService;

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
            PageRequest pageRequest = PageRequest.of(0, scrollSize + 1);
            Page<Post> page = postRepository.findAllByPostTextOrderByCreatedAtDesc(postText, createdAt, pageRequest);
            List<Post> postTitleList = page.getContent();

            ScrollPaginationCollection<Post> postCursor = ScrollPaginationCollection.of(postTitleList, scrollSize);

            return FindPostByTextResDto.of(postCursor, postRepository);
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    public ToggleCapsuleLikeResDto ToggleCapsuleLike(ToggleCapsuleLikeReqDto toggleCapsuleLikeReqDto) throws BaseException {
        jwtService.getJwt();
        try{
            Post post = postRepository.findByPostIdxAndState(toggleCapsuleLikeReqDto.getPostIdx(), BaseEntity.State.ACTIVE);
            Member member = memberRepository.findByMemberIdxAndState(toggleCapsuleLikeReqDto.getMemberIdx(), BaseEntity.State.ACTIVE);

            Optional<PostLike> postLike = postLikeRepository.findByMemberIdxAndPostIdxAndState(member, post, BaseEntity.State.ACTIVE);

            if(postLike.isPresent() && postLike.get().getState() == BaseEntity.State.ACTIVE){
                PostLike like = postLike.get();
                if ((like.getState() == BaseEntity.State.ACTIVE)) {
                    like.setState(BaseEntity.State.INACTIVE);
                } else {
                    like.setState(BaseEntity.State.ACTIVE);
                }
                postLikeRepository.save(like);
                return new ToggleCapsuleLikeResDto(like.getState());
            }
            else{
                PostLike savePostLike = new PostLike(member,post);
                postLikeRepository.save(savePostLike);
                return new ToggleCapsuleLikeResDto(savePostLike.getState());
            }


        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}

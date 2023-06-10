package com.example.demo.src.post;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.common.scroll.ScrollPaginationCollection;
import com.example.demo.src.member.entity.Member;
import com.example.demo.src.member.MemberRepository;
import com.example.demo.src.post.dto.*;
import com.example.demo.src.post.entity.CategoryScrap;
import com.example.demo.src.post.entity.Post;
import com.example.demo.src.post.entity.PostLike;
import com.example.demo.src.post.entity.PostTag;
import com.example.demo.src.post.repository.*;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryScrapRepository categoryScrapRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostTagRepository postTagRepository;


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

    public FindPostByTextResDto findPostByText(String postText, int scrollSize) throws BaseException {
        try{
            Pageable pageRequest = PageRequest.of(0, scrollSize + 1);
            Page<Post> page = postRepository.findAllByPostTextOrderByCreatedAtDesc(postText, pageRequest);
            List<Post> postTitleList = page.getContent();

            ScrollPaginationCollection<Post> postCursor = ScrollPaginationCollection.of(postTitleList, scrollSize);

            return FindPostByTextResDto.of(postCursor, postRepository);
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public FindPostByCategoryResDto findPostByCategory(Long categoryIdx, int scrollSize) {
        try{
            Pageable pageRequest = PageRequest.of(0, scrollSize + 1);
            Page<Post> page = postRepository.findAllByCategoryIdxOrderByCreatedAtDesc(categoryIdx, pageRequest);
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

    public ToggleCapsuleLikeResDto ToggleCapsuleLike(ToggleCapsuleLikeReqDto toggleCapsuleLikeReqDto) throws BaseException {
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


    public ViewImminentCapsuleResDto viewImminentCapsule(Long memberIdx) {
        try{
            Post userPost = postRepository.findAllByMemberIdxAndPostReleaseGreaterThanOrderByPostReleaseDesc(memberIdx, LocalDateTime.now());
            Optional<List<Post>> tagPostList = postTagRepository.findPostIdxByMemberIdx(memberRepository.findByMemberIdxAndState(memberIdx, BaseEntity.State.ACTIVE));
            LocalDateTime compareDateTime = LocalDateTime.MAX;
            Post tagPost = null;
            for(Post idx : tagPostList.get()) {
                Optional<Post> post = postRepository.findById(idx.getPostIdx());
                if (compareDateTime.isAfter(post.get().getPostRelease()) && post.get().getPostRelease().isAfter(LocalDateTime.now())) {
                    compareDateTime = post.get().getPostRelease();
                    tagPost = idx;
                }
            }
            Post post = null;
            if (tagPost == null || tagPost.getPostRelease().isAfter(userPost.getPostRelease()))
                post = userPost;
            else post = tagPost;
            ViewImminentCapsuleResDto res = ViewImminentCapsuleResDto.builder()
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

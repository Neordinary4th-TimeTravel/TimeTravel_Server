package com.example.demo.src.Open;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.src.Open.dto.PostCapsuleReqDto;
import com.example.demo.src.Open.dto.PostCapsuleResDto;
import com.example.demo.src.member.entity.Member;
import com.example.demo.src.post.entity.Category;
import com.example.demo.src.post.entity.Post;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class OpenService {
    private final PostOpenRepository postRepository;
    private final CategoryOpenRepositroy categoryRepository;
    private final MemberOpenRepository memberRepository;

    public PostCapsuleReqDto createCapsule(PostCapsuleResDto postCapsuleReqDto)throws BaseException {


        try{
            Category categoryIdx = categoryRepository.getByCategoryNameAndState(postCapsuleReqDto.getCategoryName(), BaseEntity.State.ACTIVE);
            Member memberIdx = memberRepository.getByMemberIdxAndState(postCapsuleReqDto.getMemberIdx(), BaseEntity.State.ACTIVE);

            boolean test = postCapsuleReqDto.isPostPublic();
            System.out.println(test);

            Post post = new Post(categoryIdx, memberIdx, postCapsuleReqDto.getPostTitle(), postCapsuleReqDto.getPostYear(), postCapsuleReqDto.getPostText(), postCapsuleReqDto.getPostSong(), postCapsuleReqDto.getPostRelease(), postCapsuleReqDto.isPostPublic());
            Post newPost = postRepository.save(post);

            return new PostCapsuleReqDto(newPost.getPostIdx());
        }catch (Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

}

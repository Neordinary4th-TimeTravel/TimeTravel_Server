package com.example.demo.src.Open;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponse;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.src.Open.dto.PostCapsuleReqDto;
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
    @Autowired
    private final JwtService jwtService;

    public Long createCapsule(PostCapsuleReqDto postCapsuleReqDto)throws BaseException {

        try{

            Category categoryIdx = categoryRepository.getByCategoryIdxAndState(postCapsuleReqDto.getCategoryIdx(), BaseEntity.State.ACTIVE);
            Member memberIdx = memberRepository.getByMemberIdxAndState(postCapsuleReqDto.getMemberIdx(), BaseEntity.State.ACTIVE);

            //비밀번호 암호화
            Post post = new Post(categoryIdx, memberIdx, postCapsuleReqDto.getPostYear(), postCapsuleReqDto.getPostText(), postCapsuleReqDto.getPostRelease(), postCapsuleReqDto.isPostPublic());
            Post newPost = postRepository.save(post);

            return newPost.getPostIdx();
        }catch (Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

}

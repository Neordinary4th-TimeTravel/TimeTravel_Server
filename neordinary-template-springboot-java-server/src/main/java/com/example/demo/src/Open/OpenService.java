package com.example.demo.src.Open;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponse;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.src.Open.dto.PostCapsuleReqDto;
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
    @Autowired
    private final JwtService jwtService;

    public Long createCapsule(PostCapsuleReqDto postCapsuleReqDto)throws BaseException {

        try{
            //비밀번호 암호화
            Post post = new Post(postCapsuleReqDto.getCategoryIdx(),postCapsuleReqDto.getPostYear(),postCapsuleReqDto.getPostText(),postCapsuleReqDto.getPostRelease(), postCapsuleReqDto.isPostPublic());
            Post newPost = postRepository.save(post);

            return newPost.getPostIdx();
        }catch (Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

}

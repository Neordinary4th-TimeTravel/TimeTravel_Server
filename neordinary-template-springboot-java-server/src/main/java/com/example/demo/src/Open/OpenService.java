package com.example.demo.src.Open;

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
    private final PostRepository postRepository;
    @Autowired
    private final JwtService jwtService;

    public void createCapsule(PostCapsuleReqDto postCapsuleReqDto){
        Post post = new Post(postCapsuleReqDto.getCategoryIdx(),postCapsuleReqDto.getPostYear(),postCapsuleReqDto.getPostText(),postCapsuleReqDto.getPostRelease(), postCapsuleReqDto.isPostPublic());
        postRepository.save(post);
    }
}

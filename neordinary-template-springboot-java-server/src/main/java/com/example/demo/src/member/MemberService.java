package com.example.demo.src.member;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.src.member.dto.JoinReqDto;
import com.example.demo.src.member.dto.JoinResDto;
import com.example.demo.src.member.entity.Member;
import com.example.demo.utils.SHA256;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public JoinResDto memberJoin(JoinReqDto req) throws BaseException{

        try{
            //비밀번호 암호화
            req.encryptPassword(SHA256.encrypt(req.getPassword()));

            Member member = req.toEntity();

            memberRepository.save(member);

            return new JoinResDto(req.getNickname());
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}

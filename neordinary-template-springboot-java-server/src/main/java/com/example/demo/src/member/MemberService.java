package com.example.demo.src.member;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.src.member.dto.JoinReqDto;
import com.example.demo.src.member.dto.JoinResDto;
import com.example.demo.src.member.dto.LoginReqDto;
import com.example.demo.src.member.dto.LoginResDto;
import com.example.demo.src.member.entity.Member;
import com.example.demo.utils.SHA256;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 유저 회원가입 - Service
     * */
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

    /**
     * 유저 로그인 - Service
     * */
    public LoginResDto memberLogin(LoginReqDto req) throws BaseException{
        Optional<Member> target;

        try{
            target = memberRepository.findByMemberEmailAndMemberPwd(req.getEmail(), SHA256.encrypt(req.getPassword()));
        }catch (Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
        //없을시 로그인 실패 판정
        target.orElseThrow(() -> new BaseException(BaseResponseStatus.FAILED_TO_LOGIN));

        return new LoginResDto(target.get().getNickname());
    }
}

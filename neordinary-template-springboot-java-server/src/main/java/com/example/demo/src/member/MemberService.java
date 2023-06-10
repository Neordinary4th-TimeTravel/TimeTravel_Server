package com.example.demo.src.member;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.src.member.dto.*;
import com.example.demo.src.member.entity.Member;
import com.example.demo.utils.JwtService;
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

    private final JwtService jwtService;

    /**
     * 유저 회원가입 - Service
     * */
    public JoinResDto memberJoin(JoinReqDto req) throws BaseException{

        try{
            //비밀번호 암호화
            req.encryptPassword(SHA256.encrypt(req.getPassword()));

            Member member = req.toEntity();

            Long memberIdx = memberRepository.save(member).getMemberIdx();



            return new JoinResDto(memberIdx ,req.getNickname(), jwtService.createJwt(memberIdx), jwtService.createRefresh());
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

        Long memberIdx = target.get().getMemberIdx();

        return new LoginResDto(memberIdx ,target.get().getNickname(), jwtService.createJwt(memberIdx), jwtService.createRefresh());
    }

    public EmailCheckResDto chechEmail(EmailCheckReqDto req) throws BaseException{
        boolean isExist;

        try{
            isExist = memberRepository.existsByMemberEmail(req.getEmail());
        }catch (Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

        if(!isExist) throw new BaseException(BaseResponseStatus.DUPLICATED_EMAIL);

        return new EmailCheckResDto(false);
    }

    public PatchNicknameResDto pathNickname(PatchNicknameReqDto req) throws BaseException{

        Optional<Member> targetOption;

        try {
            targetOption = memberRepository.findById(req.getMemberIdx());
        }catch (Exception exception) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

        targetOption.orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_USER));

        Member target = targetOption.get();

        target.patchNickname(req.getTargertNickname());

        memberRepository.save(target);

        return new PatchNicknameResDto(target.getNickname());

    }
}

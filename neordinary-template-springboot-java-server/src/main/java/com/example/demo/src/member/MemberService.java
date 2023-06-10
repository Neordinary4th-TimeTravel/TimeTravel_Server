package com.example.demo.src.member;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.src.member.dto.*;
import com.example.demo.src.member.entity.Member;
import com.example.demo.src.post.entity.Post;
import com.example.demo.src.post.repository.CommentRepository;
import com.example.demo.src.post.repository.PostLikeRepository;
import com.example.demo.src.post.repository.PostRepository;
import com.example.demo.src.post.repository.PostTagRepository;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    private final PostLikeRepository postLikeRepository;

    private final PostTagRepository postTagRepository;

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
            log.error(exception.getMessage());
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
            log.error(exception.getMessage());
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
            log.error(exception.getMessage());
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

        targetOption.orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_FIND_USER));

        Member target = targetOption.get();

        target.patchNickname(req.getTargertNickname());

        memberRepository.save(target);

        return new PatchNicknameResDto(target.getNickname());

    }

    public CapResDto getWrittenCap(Long memberIdx) throws BaseException{
        Optional<Member> targetMember;
        Optional<List<Post>> postList;

        try{
            targetMember = memberRepository.findById(memberIdx);
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

        targetMember.orElseThrow(()-> new BaseException(BaseResponseStatus.NOT_FIND_USER));

        try{
            postList = postRepository.findAllByMemberIdx(targetMember.get());
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

        postList.orElseThrow(() -> new BaseException(BaseResponseStatus.POST_NOT_FOUND));

        List<CapDto> closedList = new ArrayList<>();
        List<CapDto> opendList = new ArrayList<>();

        LocalDateTime current = LocalDateTime.now();
        for(Post post : postList.get()){
            if(current.isBefore(post.getPostRelease())){
                // 열리기 전에
                closedList.add(
                        new CapDto(
                                post.getCreatedAt().toString(),
                                post.getPostRelease().toString(),
                                post.getPostTitle()
                                )
                );
            }
            else{
                opendList.add(
                        new CapDto(
                                post.getCreatedAt().toString(),
                                post.getPostRelease().toString(),
                                post.getPostTitle()
                        )
                );
            }
        }

        return CapResDto.builder()
                .closedList(closedList)
                .openedList(opendList)
                .build();
    }

    public CapResDto getCommentedPost(Long memberIdx) {
        Optional<Member> targetMember;

        try{
            targetMember = memberRepository.findById(memberIdx);
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

        targetMember.orElseThrow(()-> new BaseException(BaseResponseStatus.NOT_FIND_USER));

        Optional<List<Long>> postIdxList;

        try{
            postIdxList = commentRepository.findPostIdxByMemberIdx(targetMember.get());
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

        List<CapDto> closedList = new ArrayList<>();
        List<CapDto> opendList = new ArrayList<>();

        postIdxList.orElseThrow(()-> new BaseException(BaseResponseStatus.COMMENT_NOT_EXIST));
        LocalDateTime current = LocalDateTime.now();

        for(Long idx : postIdxList.get()){
            Optional<Post> post = postRepository.findById(idx);

            if(current.isBefore(post.get().getPostRelease())){
                // 열리기 전에
                closedList.add(
                        new CapDto(
                                post.get().getCreatedAt().toString(),
                                post.get().getPostRelease().toString(),
                                post.get().getPostTitle()
                        )
                );
            }
            else{
                opendList.add(
                        new CapDto(
                                post.get().getCreatedAt().toString(),
                                post.get().getPostRelease().toString(),
                                post.get().getPostTitle()
                        )
                );
            }
        }

        return CapResDto.builder()
                .closedList(closedList)
                .openedList(opendList)
                .build();
    }

    public CapResDto getLikePost(Long memberIdx) {
        Optional<Member> targetMember;

        try{
            targetMember = memberRepository.findById(memberIdx);
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

        targetMember.orElseThrow(()-> new BaseException(BaseResponseStatus.NOT_FIND_USER));

        Optional<List<Post>> postIdxList;

        try{
            postIdxList  = postLikeRepository.findPostIdxByMemberIdx(targetMember.get());
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

        List<CapDto> closedList = new ArrayList<>();
        List<CapDto> opendList = new ArrayList<>();

        postIdxList.orElseThrow(() -> new BaseException(BaseResponseStatus.LIKE_NOT_EXIST));

        LocalDateTime current = LocalDateTime.now();

        for(Post idx : postIdxList.get()){
            Optional<Post> post = postRepository.findById(idx.getPostIdx());

            if(current.isBefore(post.get().getPostRelease())){
                // 열리기 전에
                closedList.add(
                        new CapDto(
                                post.get().getCreatedAt().toString(),
                                post.get().getPostRelease().toString(),
                                post.get().getPostTitle()
                        )
                );
            }
            else{
                opendList.add(
                        new CapDto(
                                post.get().getCreatedAt().toString(),
                                post.get().getPostRelease().toString(),
                                post.get().getPostTitle()
                        )
                );
            }
        }

        return CapResDto.builder()
                .closedList(closedList)
                .openedList(opendList)
                .build();
    }

    public CapResDto getTagPost(Long memberIdx) {
        Optional<Member> targetMember;

        try{
            targetMember = memberRepository.findById(memberIdx);
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

        targetMember.orElseThrow(()-> new BaseException(BaseResponseStatus.NOT_FIND_USER));

        Optional<List<Post>> postIdxList;

        try{
            postIdxList = postTagRepository.findPostIdxByMemberIdx(targetMember.get());

        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

        List<CapDto> closedList = new ArrayList<>();
        List<CapDto> opendList = new ArrayList<>();

        postIdxList.orElseThrow(() -> new BaseException(BaseResponseStatus.TAG_NOT_EXIST));

        LocalDateTime current = LocalDateTime.now();

        for(Post idx : postIdxList.get()){
            Optional<Post> post = postRepository.findById(idx.getPostIdx());

            if(current.isBefore(post.get().getPostRelease())){
                // 열리기 전에
                closedList.add(
                        new CapDto(
                                post.get().getCreatedAt().toString(),
                                post.get().getPostRelease().toString(),
                                post.get().getPostTitle()
                        )
                );
            }
            else{
                opendList.add(
                        new CapDto(
                                post.get().getCreatedAt().toString(),
                                post.get().getPostRelease().toString(),
                                post.get().getPostTitle()
                        )
                );
            }
        }

        return CapResDto.builder()
                .closedList(closedList)
                .openedList(opendList)
                .build();
    }

    public CapSightResDto getSightPost(Long memberIdx) {
        Optional<Member> targetMember;
        Optional<List<Post>> postList;

        try{
            targetMember = memberRepository.findById(memberIdx);
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

        targetMember.orElseThrow(()-> new BaseException(BaseResponseStatus.NOT_FIND_USER));

        try{
            postList = postRepository.findAllByMemberIdx(targetMember.get());
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

        postList.orElseThrow(() -> new BaseException(BaseResponseStatus.POST_NOT_FOUND));

        List<CapDto> privateList = new ArrayList<>();
        List<CapDto> publicList = new ArrayList<>();

        for(Post post : postList.get()){

            if(post.getPostPublic()){
                publicList.add(
                        new CapDto(
                                post.getCreatedAt().toString(),
                                post.getPostRelease().toString(),
                                post.getPostTitle()
                        )
                );
            }
            else {
                privateList.add(
                        new CapDto(
                                post.getCreatedAt().toString(),
                                post.getPostRelease().toString(),
                                post.getPostTitle()
                        )
                );
            }
        }

        return CapSightResDto.builder()
                .privateList(privateList)
                .publicList(publicList)
                .build();
    }
}

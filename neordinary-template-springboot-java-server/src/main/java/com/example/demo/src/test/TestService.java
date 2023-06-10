package com.example.demo.src.test;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.src.test.entity.Memo;
import com.example.demo.src.test.model.PostCommentDto;
import com.example.demo.src.test.model.GetMemoDto;
import com.example.demo.src.test.model.MemoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.common.entity.BaseEntity.State.ACTIVE;
import static com.example.demo.common.response.BaseResponseStatus.*;

@RequiredArgsConstructor
@Transactional
@Service
public class TestService {

    private final MemoRepository memoRepository;
    private final CommentRepository commentRepository;

    public void createMemo(MemoDto memoDto) throws BaseException {
        //중복
        if(checkMemo(memoDto.getMemo()) >= 1){
            throw new BaseException(POST_TEST_EXISTS_MEMO);
        }
        memoRepository.save(memoDto.toEntity());
    }
    @Transactional(readOnly = true)
    public int checkMemo(String memo){

        List<Memo> memoList = memoRepository.findByMemoAndState(memo, ACTIVE);
        return memoList.size();

    }

    @Transactional(readOnly = true)
    public List<GetMemoDto> getMemos(int startPage){

        // 페이징 예제
        PageRequest pageRequest = PageRequest.of(startPage, 5, Sort.by(Sort.Direction.DESC, "id"));
        Slice<Memo> memoSlice = memoRepository.findAllByState(ACTIVE, pageRequest);
        Slice<GetMemoDto> getMemoDtoSlice = memoSlice.map(GetMemoDto::new);
        List<GetMemoDto> getMemoDtoList = getMemoDtoSlice.getContent();

        return getMemoDtoList;

    }

    public void modifyMemo(Long memoId, MemoDto memoDto) throws BaseException {
        //중복
        if(checkMemo(memoDto.getMemo()) >= 1){
            throw new BaseException(POST_TEST_EXISTS_MEMO);
        }
        Memo memo = memoRepository.findByIdAndState(memoId, ACTIVE)
                .orElseThrow(() -> new BaseException(MODIFY_FAIL_MEMO));

        memo.updateMemo(memoDto);

    }

    public void createComment(PostCommentDto postCommentDto){
        Memo memo = memoRepository.findByIdAndState(postCommentDto.getMemoId(), ACTIVE).
                orElseThrow(() -> new BaseException(INVALID_MEMO));
        commentRepository.save(postCommentDto.toEntity(memo));
    }
}

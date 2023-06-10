package com.example.demo.src.test;


import com.example.demo.src.test.entity.Memo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import static com.example.demo.common.entity.BaseEntity.*;

public interface MemoRepository extends JpaRepository<Memo, Long> {

    List<Memo> findByMemoAndState(String memo, State state);
    //List<Memo> findAllByState(State state);

    Slice<Memo> findAllByState(State state, Pageable pageable);

    Optional<Memo> findByIdAndState(Long memoId, State state);

    // Query문을 활용한 Fetch Join 예제
//    @Query("select m from Memo m left join fetch m.commentList where m.state = :state")
//    List<Memo> findMemosUsingQuery(@Param("state") State state);

    // @EntityGraph를 활용한 Fetch Join 예제
//    @EntityGraph(attributePaths = {"commentList"})
//    @Query("select m from Memo m where m.state = :state")
//    List<Memo> findMemosUsingEntityGraph(@Param("state") State state);
}

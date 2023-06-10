package com.example.demo.src.Open;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberOpenRepository extends JpaRepository<Member, Long> {
    Member getByMemberIdxAndState(Long memberIdx, BaseEntity.State state);
}

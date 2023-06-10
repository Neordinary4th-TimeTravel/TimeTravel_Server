package com.example.demo.src.post.repository;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByMemberIdxAndState(Long memberIdx, BaseEntity.State state);
}

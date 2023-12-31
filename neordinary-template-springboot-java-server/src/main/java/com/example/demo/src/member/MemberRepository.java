package com.example.demo.src.member;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberEmailAndMemberPwd(String memberEmail, String memberPwd);

    boolean existsByMemberEmail(String memberEmail);
    Member findNicknameByMemberIdx(Long memberIdx);

    Member findByMemberIdxAndState(Long memberIdx, BaseEntity.State active);
}

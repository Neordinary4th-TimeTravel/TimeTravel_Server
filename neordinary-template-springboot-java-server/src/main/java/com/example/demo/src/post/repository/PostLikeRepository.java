package com.example.demo.src.post.repository;


import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.member.entity.Member;
import com.example.demo.src.post.entity.Post;
import com.example.demo.src.post.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByMemberIdxAndPostIdxAndState(Member memberIdx, Post postIdx, BaseEntity.State state);

    @Query(value = "select postIdx from PostLike where memberIdx = :memberIdx and state = 'ACTIVE'")
    Optional<List<Post>> findPostIdxByMemberIdx(Member memberIdx);
}

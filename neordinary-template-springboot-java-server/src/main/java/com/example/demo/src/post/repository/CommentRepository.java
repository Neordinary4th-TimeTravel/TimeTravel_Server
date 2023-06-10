package com.example.demo.src.post.repository;

import com.example.demo.src.post.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select commentIdx from Comment where memberIdx = :memberIdx")
    Optional<List<Long>> findPostIdxByMemberIdx(Long memberIdx);
}
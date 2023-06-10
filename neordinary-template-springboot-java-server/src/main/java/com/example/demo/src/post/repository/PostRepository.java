package com.example.demo.src.post.repository;

import com.example.demo.src.post.entity.Category;
import com.example.demo.src.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p.* FROM Post p WHERE p.postPublic = 1 AND p.postText LIKE CONCAT('%', :postText, '%')" +
            "ORDER BY p.createdAt DESC LIMIT :pageRequest")
    Page<Post> findAllByPostTextOrderByCreatedAtDesc(@Param("postText") String postText,
                                                     @Param("createdAt") LocalDateTime createdAt,
                                                     @Param("pageRequest") PageRequest pageRequest);
    String findPostTitleByPostIdx(Long postIdx);
}

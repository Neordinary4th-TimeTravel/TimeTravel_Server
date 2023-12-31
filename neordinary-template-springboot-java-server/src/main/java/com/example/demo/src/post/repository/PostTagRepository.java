package com.example.demo.src.post.repository;

import com.example.demo.src.member.entity.Member;
import com.example.demo.src.post.entity.Post;
import com.example.demo.src.post.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {


}

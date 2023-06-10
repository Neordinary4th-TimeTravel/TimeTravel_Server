package com.example.demo.src.Open;

import com.example.demo.src.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostOpenRepository extends JpaRepository<Post, Long> {

}

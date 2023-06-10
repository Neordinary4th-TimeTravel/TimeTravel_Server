package com.example.demo.src.post.repository;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.member.entity.Member;
import com.example.demo.src.post.entity.Category;
import com.example.demo.src.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<List<Post>> findAllByMemberIdx(Member memberIdx);

    @Query("SELECT p FROM Post p WHERE p.postPublic = true AND p.postText LIKE CONCAT('%', :postText, '%')" +
            "ORDER BY p.createdAt DESC")
    Page<Post> findAllByPostTextOrderByCreatedAtDesc(@Param("postText") String postText,
                                                     @Param("pageRequest") Pageable pageRequest);
    String findPostTitleByPostIdx(Long postIdx);

    Post findByPostIdxAndState(Long postIdx, BaseEntity.State state);

    Page<Post> findAllByCategoryIdxOrderByCreatedAtDesc(@Param("categoryIdx") Category categoryIdx,
                                                        @Param("pageRequest") Pageable pageRequest);

    Post findAllByPostIdx(Long postIdx);

    Post findAllByMemberIdxAndPostReleaseGreaterThanOrderByPostReleaseDesc(@Param("memberIdx") Member memberIdx,
                                                                         @Param("postRelease") LocalDateTime postRelease);

    @Query(nativeQuery = true, value = "select * from POST left join POSTTAG P on POST.postIdx = P.postIdx where " +
            "P.memberIdx = :memberIdx and P.state = 'ACTIVE'")
    Optional<List<Post>> findPostIdxByMemberIdx(@Param("memberIdx") Member memberIdx);

    @Query(nativeQuery = true, value = "select POST.* from POST left join POSTLIKE on POST.postIdx = POSTLIKE.postIdx" +
            " where POSTLIKE.postIdx = :memberIdx and POSTLIKE.state = 'ACTIVE'")
    Optional<List<Post>> findPostIdxByMemberIdx(@Param("memberIdx") Long memberIdx);

//    @Query("SELECT q FROM (SELECT p FROM Post p WHERE p.postYear = :postYear) q" +
//            " JOIN q.postLike l ON q.postIdx = l.postIdx" +
//            " ORDER BY COUNT(l.postIdx) DESC")
    @Query(nativeQuery = true, value = "select POST.* from POST left join POSTLIKE P on POST.postIdx = P.postIdx" +
            " where POST.postYear = :postYear order by count(P.postIdx) desc")
    Page<Post> findAllByPostYearLimitMaxCountPostIdx(@Param("postYear") int postYear,
                                                     @Param("pageRequest") Pageable pageRequest);

}

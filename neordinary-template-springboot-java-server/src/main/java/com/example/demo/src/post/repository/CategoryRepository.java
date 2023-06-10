package com.example.demo.src.post.repository;

import com.example.demo.src.member.entity.Member;
import com.example.demo.src.post.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "select categoryName from Category order by categoryName")
    List<String> findCategoryNameOrderByCategoryName();
    Category findCategoryNameByCategoryIdx(Long categoryIdx);

    @Query(nativeQuery = true, value = "select * from CATEGORY left join CATEGORYSCRAP C on CATEGORY.categoryIdx = C.categoryIdx" +
            " where C.memberIdx = :memberIdx and C.state = 'ACTIVE'")
    List<Category> findCategoryIdxByMemberIdx(@Param("memberIdx") Long memberIdx);
    Optional<Category> findByCategoryName(String categoryName);
}

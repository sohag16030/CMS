package com.cms.example.cms.feature.content;

import com.cms.example.cms.entities.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

    @Query("SELECT con FROM Content con LEFT JOIN con.cmsUser WHERE con.contentId = :contentId")
    Optional<Content> findByIdWithDetails(Long contentId);

    @Query(value = "SELECT DISTINCT con FROM Content con " +
            "JOIN FETCH con.cmsUser cms " +
            "WHERE " +
            "(:title IS NULL OR " + // Check if searchValue is null
            "LOWER(con.title) LIKE LOWER(CONCAT('%', :title, '%')) OR " + // Check title
            "LOWER(con.type) LIKE LOWER(CONCAT('%', :title, '%')) OR " + // Check type
            "LOWER(con.path) LIKE LOWER(CONCAT('%', :title, '%')) OR " + // Check path
            "LOWER(cms.userName) LIKE LOWER(CONCAT('%', :title, '%')))", // Check userName
            countQuery = "SELECT COUNT(con) FROM Content con " +
                    "JOIN con.cmsUser cms ")
    Page<Content> search(@Param("title") String title, Pageable pageable);

}

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
            "(:contentId IS NULL OR con.contentId = :contentId) AND " +
            "(:title IS NULL OR LOWER(con.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
            "(:type IS NULL OR LOWER(con.type) LIKE LOWER(CONCAT('%', :type, '%'))) AND " +
            "(:path IS NULL OR LOWER(con.path) LIKE LOWER(CONCAT('%', :path, '%'))) AND " +
            "(:cmsUserId IS NULL OR cms.cmsUserId = :cmsUserId) AND " +
            "(:userName IS NULL OR LOWER(cms.userName) LIKE LOWER(CONCAT('%', :userName, '%'))) AND " +
            "(:isActive IS NULL OR con.isActive = :isActive)",
            countQuery = "SELECT COUNT(con) FROM Content con " +
                    "JOIN con.cmsUser cms ")
    Page<Content> search(@Param("contentId") Long contentId,
                         @Param("title") String title,
                         @Param("type") String type,
                         @Param("path") String path,
                         @Param("cmsUserId") Long cmsUserId,
                         @Param("userName") String userName,
                         @Param("isActive") Boolean isActive, Pageable pageable);
}

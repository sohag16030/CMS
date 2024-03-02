package com.cms.example.cms.feature.content;

import com.cms.example.cms.entities.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

    @Query("SELECT con FROM Content con LEFT JOIN FETCH con.cmsUser WHERE con.contentId = :contentId")
    Optional<Content> findByIdWithDetails(Long contentId);

    Page<Content> findAll(Pageable pageable);

    Page<Content> findByTitleContaining(String title, Pageable pageable);

    Optional<Content> getContentByTitle(String originalFilename);
}

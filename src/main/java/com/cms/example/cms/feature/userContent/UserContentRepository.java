package com.cms.example.cms.feature.userContent;

import com.cms.example.cms.entities.UserContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserContentRepository extends JpaRepository<UserContent, Long> {

    @Query("SELECT c FROM UserContent c WHERE c.userContentId IN :contentInfoIds")
    List<UserContent> fetchUserContentsByContentsIdIn(List<Long> contentInfoIds);
}

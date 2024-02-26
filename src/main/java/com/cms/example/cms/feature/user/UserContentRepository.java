package com.cms.example.cms.feature.user;

import com.cms.example.cms.entities.UserContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserContentRepository extends JpaRepository<UserContent, Long> {
}

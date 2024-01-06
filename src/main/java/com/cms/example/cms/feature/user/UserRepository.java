package com.cms.example.cms.feature.user;

import com.cms.example.cms.entities.CmsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<CmsUser, Long> {
}

package com.cms.example.cms.auth.repository;

import com.cms.example.cms.entities.BlackListedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackListedTokenRepository extends JpaRepository<BlackListedToken,Integer> {
    BlackListedToken findByAccessToken(String token);
}

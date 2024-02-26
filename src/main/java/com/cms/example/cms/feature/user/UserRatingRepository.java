package com.cms.example.cms.feature.user;

import com.cms.example.cms.entities.UserRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRatingRepository extends JpaRepository<UserRating, Long> {
}

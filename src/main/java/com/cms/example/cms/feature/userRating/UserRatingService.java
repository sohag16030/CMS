package com.cms.example.cms.feature.userRating;

import com.cms.example.cms.entities.UserRating;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserRatingService {

    private final UserRatingRepository userRatingRepository;

    public UserRating getUserRatingById(Long userRatingId) {
        Optional<UserRating> optionalUserRating = userRatingRepository.findById(userRatingId);

        if (optionalUserRating.isPresent()) return optionalUserRating.get();
        else return null;
    }
}

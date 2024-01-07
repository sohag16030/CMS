package com.cms.example.cms.feature.userRating;

import com.cms.example.cms.common.Routes;
import com.cms.example.cms.entities.UserRating;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class UserRatingController {
    private final UserRatingService service;
    @GetMapping(Routes.USER_RATING_BY_ID_ROUTE)
    public ResponseEntity<?> getRatingById(@PathVariable Long userRatingId) {
        UserRating userRating = service.getUserRatingById(userRatingId);
        if (Objects.nonNull(userRatingId)) {
            return new ResponseEntity<>(userRating, HttpStatus.OK);
        } else {
            // TODO : throw EntityNotFoundException
            return new ResponseEntity<>("DATA NOT FOUND", HttpStatus.NOT_FOUND);
        }
    }
}

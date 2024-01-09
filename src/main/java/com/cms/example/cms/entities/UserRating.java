package com.cms.example.cms.entities;

import com.cms.example.cms.enums.RatingType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "USER_RATING")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_RATING_ID")
    private Long userRatingId;

    @Column(name = "STAR", nullable = false)
    private String star;

    @Column(name = "RATING_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private RatingType ratingType;

    @JsonIgnore
    public static Boolean isNonNull(UserRating userRating){
        return Objects.nonNull(userRating) && Objects.nonNull(userRating.getUserRatingId());
    }

}
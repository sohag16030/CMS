//package com.cms.example.cms.entities;
//
//import com.cms.example.cms.enums.RatingType;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.Objects;
//
//@Entity
//@Table(name = "USER_RATING")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class ContentRating {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "USER_RATING_ID")
//    private Long userRatingId;
//
//    @Column(name = "STAR", nullable = false)
//    private String star;
//
//    @Column(name = "RATING_TYPE", nullable = false)
//    @Enumerated(EnumType.STRING)
//    private RatingType ratingType;
//
//    @JsonIgnore
//    public static Boolean isNonNull(ContentRating userRating){
//        return Objects.nonNull(userRating) && Objects.nonNull(userRating.getUserRatingId());
//    }
//
//}
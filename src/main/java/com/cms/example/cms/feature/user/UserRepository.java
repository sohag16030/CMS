package com.cms.example.cms.feature.user;

import com.cms.example.cms.entities.CmsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<CmsUser, Long> {

    @Query("SELECT u FROM CmsUser u LEFT JOIN FETCH u.userRating r WHERE u.cmsUserId= :cmsUserId")
    Optional<CmsUser> fetchRatingInfoByRatingId(@Param("cmsUserId") Long cmsUserId);

    @Query("SELECT u FROM CmsUser u " +
            "JOIN FETCH u.addresses a " +
            "JOIN FETCH a.division " +
            "JOIN FETCH a.district " +
            "JOIN FETCH a.upazila " +
            "WHERE u.cmsUserId = :cmsUserId")
    List<CmsUser> fetchAddressInfoByUserId(@Param("cmsUserId") Long cmsUserId);

    @Query("SELECT u FROM CmsUser u " +
            "JOIN FETCH u.academicInfos a " +
            "JOIN a.subjects s " +
            "WHERE u.cmsUserId = :cmsUserId")
    List<CmsUser> fetchAcademicInfoByUserId(@Param("cmsUserId") Long cmsUserId);

}

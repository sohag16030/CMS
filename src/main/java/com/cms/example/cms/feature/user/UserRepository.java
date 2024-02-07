package com.cms.example.cms.feature.user;

import com.cms.example.cms.entities.AcademicInfo;
import com.cms.example.cms.entities.CmsUser;
import com.cms.example.cms.entities.District;
import com.cms.example.cms.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<CmsUser, Long> {

    @Query("SELECT u FROM CmsUser u " +
            "JOIN FETCH u.userRating r " +
            "JOIN FETCH u.addresses a " +
            "JOIN FETCH a.division " +
            "JOIN FETCH a.district " +
            "JOIN FETCH a.upazila " +
            "WHERE u.cmsUserId = :cmsUserId")
    Optional<CmsUser> fetchRatingAddressInfoByUserId(@Param("cmsUserId") Long cmsUserId);

    @Query("SELECT a FROM AcademicInfo a " +
            "JOIN FETCH a.cmsUser c " +
            //"JOIN FETCH a.subjects s " +
            "WHERE c.cmsUserId = :cmsUserId")
    List<AcademicInfo> fetchAcademicInfoByUserId(@Param("cmsUserId") Long cmsUserId);

}

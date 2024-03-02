package com.cms.example.cms.feature.user;

import com.cms.example.cms.entities.CmsUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CmsUserRepository extends JpaRepository<CmsUser, Long> {

    @Query("SELECT u FROM CmsUser u " +
            "JOIN FETCH u.addresses a " +
            "JOIN FETCH a.division " +
            "JOIN FETCH a.district " +
            "JOIN FETCH a.upazila " +
            "WHERE u.cmsUserId = :cmsUserId")
    Optional<CmsUser> fetchUserAddressInfoByUserId(@Param("cmsUserId") Long cmsUserId);

    @Query("SELECT u FROM CmsUser u " +
            "JOIN FETCH u.academicInfos a " +
            "JOIN a.subjects s " +
            "WHERE u.cmsUserId = :cmsUserId")
    CmsUser fetchAcademicInfoByUserId(@Param("cmsUserId") Long cmsUserId);

    Page<CmsUser> findAll(Pageable pageable);

    Page<CmsUser> findByNameContaining(String name, Pageable pageable);

    Optional<CmsUser> findByUserName(String username);
}

package com.cms.example.cms.feature.user;

import com.cms.example.cms.entities.CmsUser;
import com.cms.example.cms.enums.Gender;
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
            "JOIN FETCH  u.addresses a " +
            "JOIN FETCH a.division " +
            "JOIN FETCH a.district " +
            "JOIN FETCH a.upazila " +
            "WHERE u.cmsUserId = :cmsUserId")
    Optional<CmsUser> fetchUserAddressInfoByUserId(@Param("cmsUserId") Long cmsUserId);

    @Query("SELECT DISTINCT u FROM CmsUser u " +
            "JOIN FETCH u.academicInfos a " +
            "JOIN a.subjects s " +
            "WHERE u.cmsUserId = :cmsUserId")
    CmsUser fetchAcademicInfoByUserId(@Param("cmsUserId") Long cmsUserId);

    @Query("SELECT DISTINCT cms FROM CmsUser cms " +
            "WHERE " +
            "(:cmsUserId IS NULL OR cms.cmsUserId = :cmsUserId) AND " +
            "(:userName IS NULL OR LOWER(cms.userName) LIKE LOWER(CONCAT('%', :userName, '%'))) AND " +
            "(:roles IS NULL OR LOWER(cms.roles) LIKE LOWER(CONCAT('%', :roles, '%'))) AND " +
            "(:mobileNumber IS NULL OR LOWER(cms.mobileNumber) LIKE LOWER(CONCAT('%', :mobileNumber, '%'))) AND " +
            "(:email IS NULL OR LOWER(cms.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
            "(:name IS NULL OR LOWER(cms.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:gender IS NULL OR cms.gender = :gender) AND " +
            "(:isActive IS NULL OR cms.isActive = :isActive)")
    Page<CmsUser> search(@Param("cmsUserId") Long cmsUserId,
                         @Param("userName") String userName,
                         @Param("roles") String roles,
                         @Param("mobileNumber") String mobileNumber,
                         @Param("email") String email,
                         @Param("name") String name,
                         @Param("gender") Gender gender,
                         @Param("isActive") Boolean isActive, Pageable pageable);

    Optional<CmsUser> findByUserName(String username);
}

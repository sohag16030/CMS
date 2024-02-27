package com.cms.example.cms.feature.user;

import com.cms.example.cms.entities.CmsUser;
import com.cms.example.cms.entities.Division;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    Optional<CmsUser> fetchUserRatingWithAddressInfoByUserId(@Param("cmsUserId") Long cmsUserId);

    @Query("SELECT u FROM CmsUser u " +
            "JOIN FETCH u.academicInfos a " +
            "JOIN a.subjects s " +
            "WHERE u.cmsUserId = :cmsUserId")
    CmsUser fetchAcademicInfoByUserId(@Param("cmsUserId") Long cmsUserId);

    Page<CmsUser> findAll(Pageable pageable);

    Page<CmsUser> findByNameContaining(String name, Pageable pageable);

//    @Query("SELECT cms FROM CmsUser cms " +
//            "WHERE " +
//            "(:cmsUserId IS NULL OR cms.cmsUserId = :cmsUserId) AND " +
//            "(:mobileNumber IS NULL OR LOWER(cms.mobileNumber) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
//            "(:nameLocal IS NULL OR LOWER(div.nameLocal) LIKE LOWER(CONCAT('%', :nameLocal, '%'))) AND " +
//            "(:active IS NULL OR div.active = :active)")
//    Page<Division> search(@Param("cmsUserId") Long cmsUserId,
//                          @Param("mobileNumber") String mobileNumber,
//                          @Param("nameLocal") String nameLocal,
//                          @Param("active") Boolean active, Pageable pageable);

}

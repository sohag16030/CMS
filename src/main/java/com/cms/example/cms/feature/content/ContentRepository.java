package com.cms.example.cms.feature.content;

import com.cms.example.cms.entities.CmsUser;
import com.cms.example.cms.entities.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

    @Query("SELECT con FROM Content con LEFT JOIN FETCH con.cmsUser WHERE con.contentId = :contentId")
    Optional<Content> findByIdWithDetails(Long contentId);

    @Query("SELECT DISTINCT con FROM Content con " +
            "JOIN con.cmsUser cms " +
            "WHERE " +
            "(:contentId IS NULL OR con.contentId = :contentId) AND " +
            "(:title IS NULL OR LOWER(con.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
            "(:type IS NULL OR LOWER(con.type) LIKE LOWER(CONCAT('%', :type, '%'))) AND " +
            "(:path IS NULL OR LOWER(con.path) LIKE LOWER(CONCAT('%', :path, '%'))) AND " +
            "(:cmsUserId IS NULL OR cms.cmsUserId = :cmsUserId) AND " +
            "(:userName IS NULL OR LOWER(cms.userName) LIKE LOWER(CONCAT('%', :userName, '%'))) AND " +
            "(:roles IS NULL OR LOWER(cms.roles) LIKE LOWER(CONCAT('%', :roles, '%'))) AND " +
            "(:mobileNumber IS NULL OR LOWER(cms.mobileNumber) LIKE LOWER(CONCAT('%', :mobileNumber, '%'))) AND " +
            "(:email IS NULL OR LOWER(cms.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
            "(:name IS NULL OR LOWER(cms.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
//            "(:gender IS NULL OR LOWER(cms.gender) LIKE LOWER(CONCAT('%', :gender, '%'))) AND " +
//            "(:userStatus IS NULL OR LOWER(cms.userStatus) LIKE LOWER(CONCAT('%', :userStatus, '%'))) AND " +
            "(:isUserActive IS NULL OR cms.isActive = :isUserActive) AND " +
            "(:isActive IS NULL OR con.isActive = :isActive)")
    Page<Content> search(@Param("contentId") Long contentId,
                         @Param("title") String title,
                         @Param("type") String type,
                         @Param("path") String path,
                         @Param("cmsUserId") Long cmsUserId,
                         @Param("userName") String userName,
                         @Param("roles") String roles,
                         @Param("mobileNumber") String mobileNumber,
                         @Param("email") String email,
                         @Param("name") String name,
//                         @Param("gender") String gender,
//                         @Param("userStatus") String userStatus,
                         @Param("isUserActive") Boolean isUserActive,
                         @Param("isActive") Boolean isActive, Pageable pageable);
}

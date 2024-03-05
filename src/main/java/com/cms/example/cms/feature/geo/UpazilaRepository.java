package com.cms.example.cms.feature.geo;

import com.cms.example.cms.entities.Upazila;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UpazilaRepository extends JpaRepository<Upazila, Long> {

    @Query("SELECT u FROM Upazila u LEFT JOIN FETCH u.district WHERE u.upazilaId = :upazilaId")
    Optional<Upazila> findByIdWithDetails(@Param("upazilaId") Long upazilaId);

    @Query(value = "SELECT DISTINCT u FROM Upazila u " +
            "JOIN FETCH u.district dis " +
            "JOIN FETCH dis.division div " +
            "WHERE " +
            "(:upazilaId IS NULL OR u.upazilaId = :upazilaId) AND " +
            "(:upazilaName IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT('%', :upazilaName, '%'))) AND " +
            "(:active IS NULL OR u.active = :active) AND " +
            "(:districtId IS NULL OR dis.districtId = :districtId) AND " +
            "(:districtName IS NULL OR LOWER(dis.name) LIKE LOWER(CONCAT('%', :districtName, '%'))) AND " +
            "(:divisionName IS NULL OR LOWER(div.name) LIKE LOWER(CONCAT('%', :divisionName, '%'))) AND " +
            "(:divisionId IS NULL OR div.divisionId = :divisionId)",
            countQuery = "SELECT COUNT(DISTINCT u) FROM Upazila u " +
                    "JOIN u.district dis " +
                    "JOIN dis.division div ")
    Page<Upazila> search(@Param("divisionId") Long divisionId,
                         @Param("divisionName") String divisionName,
                         @Param("districtId") Long districtId,
                         @Param("districtName") String districtName,
                         @Param("upazilaId") Long upazilaId,
                         @Param("upazilaName") String upazilaName,
                         @Param("active") Boolean active, Pageable pageable);

}


package com.cms.example.cms.feature.geo.districtService;

import com.cms.example.cms.entities.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
    @Query("SELECT d FROM District d LEFT JOIN FETCH d.upazilas WHERE d.districtId = :districtId")
    Optional<District> findByIdWithDetails(@Param("districtId") Long districtId);
}

package com.cms.example.cms.feature.geo;

import com.cms.example.cms.entities.District;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {

    @Query("SELECT d FROM District d LEFT JOIN FETCH d.upazilas LEFT JOIN FETCH d.division WHERE d.districtId = :districtId")
    Optional<District> findByIdWithDetails(@Param("districtId") Long districtId);

    @Query("SELECT d FROM District d LEFT JOIN FETCH d.upazilas WHERE d.districtId IN :districtsIds")
    List<District> fetchUpazilaByDistrictIdIn(@Param("districtsIds") List<Long> districtsIds);

    @Query(value = "SELECT DISTINCT dis FROM District dis " +
            "JOIN FETCH dis.division div " +
            "WHERE " +
            "(:divisionId IS NULL OR div.divisionId = :divisionId) AND " +
            "(:divisionName IS NULL OR LOWER(div.name) LIKE LOWER(CONCAT('%', :divisionName, '%'))) AND " +
            "(:districtId IS NULL OR dis.districtId = :districtId) AND " +
            "(:districtName IS NULL OR LOWER(dis.name) LIKE LOWER(CONCAT('%', :districtName, '%'))) AND " +
            "(:active IS NULL OR dis.active = :active)",
            countQuery = "SELECT COUNT(dis) FROM District dis")
    Page<District> search(@Param("divisionId") Long divisionId,
                          @Param("divisionName") String divisionName,
                          @Param("districtId") Long districtId,
                          @Param("districtName") String districtName,
                          @Param("active") Boolean active, Pageable pageable);
}


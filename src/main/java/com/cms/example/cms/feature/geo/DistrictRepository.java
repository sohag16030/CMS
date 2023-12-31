package com.cms.example.cms.feature.geo;

import com.cms.example.cms.entities.District;
import com.cms.example.cms.entities.Division;
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

    @Query("SELECT d FROM District d WHERE " +
            "(:districtId IS NULL OR d.districtId = :districtId) AND " +
            "(:name IS NULL OR d.name = :name) AND " +
            "(:nameLocal IS NULL OR d.nameLocal = :nameLocal) AND " +
            "(:active IS NULL OR d.active = :active)")
    List<District> findByFilter(@Param("districtId") Long districtId,
                                @Param("name") String name,
                                @Param("nameLocal") String nameLocal,
                                @Param("active") Boolean active);
}


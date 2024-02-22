package com.cms.example.cms.feature.geo;

import com.cms.example.cms.entities.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DivisionRepository extends JpaRepository<Division, Long> {

    @Query("SELECT d FROM Division d LEFT JOIN FETCH d.districts WHERE d.divisionId = :divisionId")
    Optional<Division> findByIdWithDetails(@Param("divisionId") Long divisionId);

    @Query("SELECT div FROM Division div " +
            "JOIN div.districts dis " +
            "JOIN dis.upazilas u " +
            "WHERE " +
            "(:divisionId IS NULL OR div.divisionId = :divisionId) AND " +
            "(:name IS NULL OR LOWER(div.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:nameLocal IS NULL OR LOWER(div.nameLocal) LIKE LOWER(CONCAT('%', :nameLocal, '%'))) AND " +
            "(:active IS NULL OR div.active = :active) AND " +
            "(:districtId IS NULL OR dis.districtId = :districtId) AND " +
            "(:upazilaId IS NULL OR u.upazilaId = :upazilaId)")
    List<Division> search(@Param("divisionId") Long divisionId,
                          @Param("districtId") Long districtId,
                          @Param("upazilaId") Long upazilaId,
                          @Param("name") String name,
                          @Param("nameLocal") String nameLocal,
                          @Param("active") Boolean active);
}

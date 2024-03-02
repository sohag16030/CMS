package com.cms.example.cms.feature.geo;

import com.cms.example.cms.entities.Division;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DivisionRepository extends JpaRepository<Division, Long> {

    @Query("SELECT d FROM Division d LEFT JOIN FETCH d.districts WHERE d.divisionId = :divisionId")
    Optional<Division> findByIdWithDetails(@Param("divisionId") Long divisionId);

    @Query("SELECT DISTINCT div FROM Division div " +
            "WHERE " +
            "(:divisionId IS NULL OR div.divisionId = :divisionId) AND " +
            "(:name IS NULL OR LOWER(div.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:active IS NULL OR div.active = :active)")
    Page<Division> search(@Param("divisionId") Long divisionId,
                          @Param("name") String name,
                          @Param("active") Boolean active, Pageable pageable);
}

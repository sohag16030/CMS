package com.cms.example.cms.feature.geo;

import com.cms.example.cms.entities.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DivisionRepository extends JpaRepository<Division, Long> {
    @Query("SELECT d FROM Division d WHERE d.divisionId = :divisionId")
    Optional<Division> findByIdWithDetails(@Param("divisionId") Long divisionId);
}

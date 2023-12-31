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

    @Query("SELECT d FROM Division d WHERE " +
            "(:divisionId IS NULL OR d.divisionId = :divisionId) AND " +
            "(:name IS NULL OR d.name ILIKE %:name%) AND " +
            "(:nameLocal IS NULL OR d.nameLocal ILIKE %:nameLocal%) AND " +
            "(:active IS NULL OR d.active = :active)")

    List<Division> findByFilter(@Param("divisionId") Long divisionId,
                                @Param("name") String name,
                                @Param("nameLocal") String nameLocal,
                                @Param("active") Boolean active);

}

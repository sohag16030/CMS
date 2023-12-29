package com.cms.example.cms.feature.geo;

import com.cms.example.cms.entities.Upazila;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UpazilaRepository extends JpaRepository<Upazila, Long> {

    @Query("SELECT u FROM Upazila u LEFT JOIN FETCH u.district WHERE u.upazilaId = :upazilaId")
    Optional<Upazila> findByIdWithDetails(@Param("upazilaId") Long upazilaId);
}


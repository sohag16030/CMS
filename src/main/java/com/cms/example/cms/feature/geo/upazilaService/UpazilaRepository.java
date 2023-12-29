package com.cms.example.cms.feature.geo.upazilaService;

import com.cms.example.cms.entities.Upazila;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpazilaRepository extends JpaRepository<Upazila, Long> {
}

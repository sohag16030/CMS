package com.cms.example.cms.feature.geo;

import com.cms.example.cms.entities.Division;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DivisionService {

    @Autowired
    private DivisionRepository divisionRepository;

    @Transactional
    public Optional<Division> getDivisionById(Long divisionId) {
        return divisionRepository.findById(divisionId);
    }

    public Optional<Division> getDivisionWithDetailsById(Long divisionId) {
        return divisionRepository.findByIdWithDetails(divisionId);
    }
}

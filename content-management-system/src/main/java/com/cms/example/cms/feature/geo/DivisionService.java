package com.cms.example.cms.feature.geo;

import com.cms.example.cms.entities.Division;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DivisionService {

    @Autowired
    private DivisionRepository divisionRepository;

    public Division getDivisionById(Long divisionId) {
        return divisionRepository.findById(divisionId)
                .orElseThrow(() -> new ResourceNotFoundException("Division not found with id: " + divisionId));
    }
}

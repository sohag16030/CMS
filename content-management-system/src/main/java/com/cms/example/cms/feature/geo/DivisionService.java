package com.cms.example.cms.feature.geo;

import com.cms.example.cms.entities.Division;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DivisionService {

    private final DivisionRepository divisionRepository;

    @Transactional
    public Optional<Division> getDivisionById(Long divisionId) {
        return divisionRepository.findById(divisionId);
    }

    public Optional<Division> getDivisionWithDetailsById(Long divisionId) {
        return divisionRepository.findByIdWithDetails(divisionId);
    }
}

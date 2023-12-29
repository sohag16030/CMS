package com.cms.example.cms.feature.geo.upazilaService;

import com.cms.example.cms.entities.Upazila;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpazilaService {

    private final UpazilaRepository districtRepository;

    public Optional<Upazila> getUpazilaById(Long districtId) {
        return districtRepository.findById(districtId);
    }
}

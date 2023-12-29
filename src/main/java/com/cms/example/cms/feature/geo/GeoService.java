package com.cms.example.cms.feature.geo;

import com.cms.example.cms.entities.District;
import com.cms.example.cms.entities.Division;
import com.cms.example.cms.entities.Upazila;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GeoService {

    private final DivisionRepository divisionRepository;
    private final DistrictRepository districtRepository;
    private final UpazilaRepository upazilaRepository;

    public Optional<Division> getDivisionById(Long divisionId) {
        return divisionRepository.findById(divisionId);
    }
    public Optional<Division> getDivisionDetailsById(Long divisionId) {
        return divisionRepository.findByIdWithDetails(divisionId);
    }
    public Optional<District> getDistrictById(Long districtId) {
        return districtRepository.findById(districtId);
    }
    public Optional<District> getDistrictDetailsById(Long districtId) {
        return districtRepository.findByIdWithDetails(districtId);
    }
    public Optional<Upazila> getUpazilaById(Long districtId) {
        return upazilaRepository.findById(districtId);
    }
    public Optional<Upazila> getUpazilaWithDetailsById(Long upazilaId) {
        return upazilaRepository.findByIdWithDetails(upazilaId);
    }
}

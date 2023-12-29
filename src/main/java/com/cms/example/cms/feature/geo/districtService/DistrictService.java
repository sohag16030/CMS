package com.cms.example.cms.feature.geo.districtService;

import com.cms.example.cms.entities.District;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DistrictService {

    private final DistrictRepository districtRepository;

    public Optional<District> getDistrictById(Long districtId) {
        return Optional.of(districtRepository.findById(districtId).get());
    }
    public Optional<District> getDistrictDetailsById(Long districtId) {
        return districtRepository.findByIdWithDetails(districtId);
    }
}

package com.cms.example.cms.feature.geo;

import com.cms.example.cms.entities.District;
import com.cms.example.cms.entities.Division;
import com.cms.example.cms.entities.Upazila;
import com.cms.example.cms.enums.EntityFetchType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GeoService {

    private final DivisionRepository divisionRepository;
    private final DistrictRepository districtRepository;
    private final UpazilaRepository upazilaRepository;

	public Division getDivisionById(Long divisionId, EntityFetchType fetchType) {
		Optional<Division> optionalDivision = EntityFetchType.NO_FETCH.equals(fetchType) ?
				divisionRepository.findById(divisionId) :
				divisionRepository.findByIdWithDetails(divisionId);
		if (optionalDivision.isPresent()) return optionalDivision.get();
		else return null;
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

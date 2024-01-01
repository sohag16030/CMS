package com.cms.example.cms.feature.geo;

import com.cms.example.cms.entities.District;
import com.cms.example.cms.entities.Division;
import com.cms.example.cms.entities.Upazila;
import com.cms.example.cms.enums.EntityFetchType;
import com.cms.example.cms.dto.GeoFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public List<Division> getDivisionsByFilter(GeoFilter filter) {
        if (filter.isEmpty()) {
            return divisionRepository.findAll();
        }
        return divisionRepository.findByFilter(filter.getDivisionId(),filter.getName(),filter.getNameLocal(),filter.getActive());
    }

    public District getDistrictById(Long districtId, EntityFetchType fetchType) {
        Optional<District> optionalDistrict = EntityFetchType.NO_FETCH.equals(fetchType) ?
                districtRepository.findById(districtId) :
                districtRepository.findByIdWithDetails(districtId);
        if (optionalDistrict.isPresent()) return optionalDistrict.get();
        else return null;
    }

    public List<District> getDistrictsByFilter(GeoFilter filter) {
        if (filter.isEmpty()) {
            return districtRepository.findAll();
        }
        return districtRepository.findByFilter(filter.getDistrictId(),filter.getName(),filter.getNameLocal(),filter.getActive(),filter.getDivisionId());
    }

    public Upazila getUpazilaById(Long upazilaId, EntityFetchType fetchType) {
        Optional<Upazila> optionalUpazila = EntityFetchType.NO_FETCH.equals(fetchType) ?
                upazilaRepository.findById(upazilaId) :
                upazilaRepository.findByIdWithDetails(upazilaId);
        if (optionalUpazila.isPresent()) return optionalUpazila.get();
        else return null;
    }
}

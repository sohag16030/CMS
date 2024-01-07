package com.cms.example.cms.feature.geo;

import com.cms.example.cms.entities.District;
import com.cms.example.cms.entities.Division;
import com.cms.example.cms.entities.Upazila;
import com.cms.example.cms.enums.EntityFetchType;
import com.cms.example.cms.dto.GeoFilterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GeoService {

    private final DivisionRepository divisionRepository;
    private final DistrictRepository districtRepository;
    private final UpazilaRepository upazilaRepository;

    public Division getDivisionById(Long divisionId, EntityFetchType fetchType) {
        Optional<Division> optionalDivision = null;
        if (EntityFetchType.NO_FETCH.equals(fetchType)) {
            optionalDivision = divisionRepository.findById(divisionId);

        } else {
            optionalDivision = divisionRepository.findByIdWithDetails(divisionId);
//            List<District> districts = optionalDivision.get().getDistricts();
//            List<Long> districtIds = districts.stream().map(District::getDistrictId).collect(Collectors.toList());
//            districtRepository.fetchUpazilaByDistrictIdIn(districtIds);
        }
        if (optionalDivision.isPresent()) {
            return optionalDivision.get();
        } else return null;
    }

    public List<Division> getDivisionsByFilter(GeoFilterDto filter) {
        return  divisionRepository.search(filter.getDivisionId(), filter.getDistrictId(), filter.getUpazilaId(), filter.getName(), filter.getNameLocal(), filter.getActive());
    }

    public District getDistrictById(Long districtId, EntityFetchType fetchType) {
        Optional<District> optionalDistrict = EntityFetchType.NO_FETCH.equals(fetchType) ?
                districtRepository.findById(districtId) :
                districtRepository.findByIdWithDetails(districtId);
        if (optionalDistrict.isPresent()) return optionalDistrict.get();
        else return null;
    }

    public List<District> getDistrictsByFilter(GeoFilterDto filter) {
        return districtRepository.search(filter.getDivisionId(), filter.getDistrictId(), filter.getUpazilaId(), filter.getName(), filter.getNameLocal(), filter.getActive());
    }

    public Upazila getUpazilaById(Long upazilaId, EntityFetchType fetchType) {
        Optional<Upazila> optionalUpazila = EntityFetchType.NO_FETCH.equals(fetchType) ?
                upazilaRepository.findById(upazilaId) :
                upazilaRepository.findByIdWithDetails(upazilaId);
        if (optionalUpazila.isPresent()) return optionalUpazila.get();
        else return null;
    }

    public List<Upazila> getUpazilaByFilter(GeoFilterDto filter) {
        return upazilaRepository.search(filter.getDivisionId(), filter.getDistrictId(), filter.getUpazilaId(), filter.getName(), filter.getNameLocal(), filter.getActive());
    }
}

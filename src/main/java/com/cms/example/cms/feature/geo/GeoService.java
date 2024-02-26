package com.cms.example.cms.feature.geo;

import com.cms.example.cms.dto.PaginatedCmsUserResponse;
import com.cms.example.cms.dto.PaginatedDistrictResponse;
import com.cms.example.cms.dto.PaginatedDivisionResponse;
import com.cms.example.cms.dto.PaginatedUpazilaResponse;
import com.cms.example.cms.entities.CmsUser;
import com.cms.example.cms.entities.District;
import com.cms.example.cms.entities.Division;
import com.cms.example.cms.entities.Upazila;
import com.cms.example.cms.enums.EntityFetchType;
import com.cms.example.cms.dto.GeoFilterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            List<District> districts = optionalDivision.get().getDistricts();
            List<Long> districtIds = districts.stream().map(District::getDistrictId).collect(Collectors.toList());
            districtRepository.fetchUpazilaByDistrictIdIn(districtIds);
        }
        if (optionalDivision.isPresent()) {
            return optionalDivision.get();
        } else return null;
    }

    public PaginatedDivisionResponse getDivisionsByFilter(GeoFilterDto filter,Pageable pageable) {
        Page<Division> divisions =  divisionRepository.search(filter.getDivisionId(), filter.getName(), filter.getNameLocal(), filter.getActive(),pageable);
        return PaginatedDivisionResponse.builder()
                .numberOfItems(divisions.getTotalElements()).numberOfPages(divisions.getTotalPages())
                .divisionList(divisions.getContent())
                .build();
    }

    public District getDistrictById(Long districtId, EntityFetchType fetchType) {
        Optional<District> optionalDistrict = EntityFetchType.NO_FETCH.equals(fetchType) ?
                districtRepository.findById(districtId) :
                districtRepository.findByIdWithDetails(districtId);
        if (optionalDistrict.isPresent()) return optionalDistrict.get();
        else return null;
    }

    public PaginatedDistrictResponse getDistrictsByFilter(GeoFilterDto filter,Pageable pageable) {
        Page<District> districts =  districtRepository.search(filter.getDivisionId(),filter.getDistrictId(), filter.getName(), filter.getNameLocal(), filter.getActive(),pageable);
        return PaginatedDistrictResponse.builder()
                .numberOfItems(districts.getTotalElements()).numberOfPages(districts.getTotalPages())
                .districtList(districts.getContent())
                .build();
    }

    public Upazila getUpazilaById(Long upazilaId, EntityFetchType fetchType) {
        Optional<Upazila> optionalUpazila = EntityFetchType.NO_FETCH.equals(fetchType) ?
                upazilaRepository.findById(upazilaId) :
                upazilaRepository.findByIdWithDetails(upazilaId);
        if (optionalUpazila.isPresent()) return optionalUpazila.get();
        else return null;
    }

    public PaginatedUpazilaResponse getUpazilaByFilter(GeoFilterDto filter,Pageable pageable) {
        Page<Upazila> upazilas =  upazilaRepository.search(filter.getDivisionId(),filter.getDistrictId(),filter.getUpazilaId(), filter.getName(), filter.getNameLocal(), filter.getActive(),pageable);
        return PaginatedUpazilaResponse.builder()
                .numberOfItems(upazilas.getTotalElements()).numberOfPages(upazilas.getTotalPages())
                .upazilaList(upazilas.getContent())
                .build();
    }
}

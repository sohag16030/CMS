package com.cms.example.cms.feature.geo;

import com.cms.example.cms.dto.paginatedResponseDto.PaginatedDistrictResponse;
import com.cms.example.cms.dto.paginatedResponseDto.PaginatedDivisionResponse;
import com.cms.example.cms.dto.paginatedResponseDto.PaginatedUpazilaResponse;
import com.cms.example.cms.entities.District;
import com.cms.example.cms.entities.Division;
import com.cms.example.cms.entities.Upazila;
import com.cms.example.cms.enums.EntityFetchType;
import com.cms.example.cms.dto.listDataFilterRequestDto.GeoFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        Optional<Division> optionalDivision;
        if (EntityFetchType.NO_FETCH.equals(fetchType)) {
            optionalDivision = divisionRepository.findById(divisionId);

        } else {
            optionalDivision = divisionRepository.findByIdWithDetails(divisionId);
            List<District> districts = new ArrayList<>();
            if (optionalDivision.isPresent())
                districts = optionalDivision.get().getDistricts();
            List<Long> districtIds = districts.stream().map(District::getDistrictId).collect(Collectors.toList());
            districtRepository.fetchUpazilaByDistrictIdIn(districtIds);
        }
        return optionalDivision.orElse(null);
    }

    public PaginatedDivisionResponse getDivisionsByFilter(GeoFilter filter, Pageable pageable) {
        Page<Division> divisions = divisionRepository.search(filter.getDivisionId(), filter.getDivisionName(), filter.getActive(), pageable);
        return PaginatedDivisionResponse.builder()
                .numberOfItems(divisions.getTotalElements()).numberOfPages(divisions.getTotalPages())
                .divisionList(divisions.getContent())
                .build();
    }

    public District getDistrictById(Long districtId, EntityFetchType fetchType) {
        Optional<District> optionalDistrict = EntityFetchType.NO_FETCH.equals(fetchType) ?
                districtRepository.findById(districtId) :
                districtRepository.findByIdWithDetails(districtId);
        return optionalDistrict.orElse(null);
    }

    public PaginatedDistrictResponse getDistrictsByFilter(GeoFilter filter, Pageable pageable) {
        Page<District> districts = districtRepository.search(filter.getDivisionId(), filter.getDivisionName(), filter.getDistrictId(), filter.getDistrictName(), filter.getActive(), pageable);
        return PaginatedDistrictResponse.builder()
                .numberOfItems(districts.getTotalElements()).numberOfPages(districts.getTotalPages())
                .districtList(districts.getContent())
                .build();
    }

    public Upazila getUpazilaById(Long upazilaId, EntityFetchType fetchType) {
        Optional<Upazila> optionalUpazila = EntityFetchType.NO_FETCH.equals(fetchType) ?
                upazilaRepository.findById(upazilaId) :
                upazilaRepository.findByIdWithDetails(upazilaId);
        return optionalUpazila.orElse(null);
    }

    public PaginatedUpazilaResponse getUpazilaByFilter(GeoFilter filter, Pageable pageable) {
        Page<Upazila> upazilas = upazilaRepository.search(filter.getDivisionId(), filter.getDivisionName(), filter.getDistrictId(), filter.getDistrictName(), filter.getUpazilaId(), filter.getUpazilaName(), filter.getActive(), pageable);
        return PaginatedUpazilaResponse.builder()
                .numberOfItems(upazilas.getTotalElements()).numberOfPages(upazilas.getTotalPages())
                .upazilaList(upazilas.getContent())
                .build();
    }
}

package com.cms.example.cms.feature.geo;

import com.cms.example.cms.common.Routes;
import com.cms.example.cms.dto.paginatedResponseDto.PaginatedDistrictResponse;
import com.cms.example.cms.dto.paginatedResponseDto.PaginatedDivisionResponse;
import com.cms.example.cms.dto.paginatedResponseDto.PaginatedUpazilaResponse;
import com.cms.example.cms.entities.District;
import com.cms.example.cms.entities.Division;
import com.cms.example.cms.entities.Upazila;
import com.cms.example.cms.enums.EntityFetchType;
import com.cms.example.cms.dto.listDataFilterRequestDto.GeoFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class GeoController {

    private final GeoService service;

    @GetMapping(Routes.DIVISION_BY_ID_ROUTE)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_MODERATOR')")
    public ResponseEntity<?> getDivisionById(@PathVariable Long divisionId,
                                             @RequestParam(defaultValue = "NO_FETCH") EntityFetchType fetchType) {
        Division division = service.getDivisionById(divisionId, fetchType);

        if (Objects.nonNull(division)) {
            return new ResponseEntity<>(division, HttpStatus.OK);
        } else {
            // TODO : throw EntityNotFoundException
            return new ResponseEntity<>("DATA NOT FOUND", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(Routes.DIVISION_LIST_ROUTE)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_MODERATOR')")
    public ResponseEntity<?> getAllDivisionsByFilter(GeoFilter filter, Pageable pageable) {
        PaginatedDivisionResponse paginatedDivisionResponse = service.getDivisionsByFilter(filter,pageable);
        if (paginatedDivisionResponse == null) {
            return new ResponseEntity<>("DATA NOT FOUND", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(paginatedDivisionResponse, HttpStatus.OK);
    }

    @GetMapping(Routes.DISTRICT_BY_ID_ROUTE)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_MODERATOR')")
    public ResponseEntity<?> getDistrictById(@PathVariable Long districtId,
                                             @RequestParam(defaultValue = "NO_FETCH") EntityFetchType fetchType) {
        District district = service.getDistrictById(districtId, fetchType);
        if (Objects.nonNull(districtId)) {
            return new ResponseEntity<>(district, HttpStatus.OK);
        } else {
            // TODO : throw EntityNotFoundException
            return new ResponseEntity<>("DATA NOT FOUND", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(Routes.DISTRICT_LIST_ROUTE)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_MODERATOR')")
    public ResponseEntity<?> getAllDistrictsByFilter(GeoFilter filter, Pageable pageable) {

        PaginatedDistrictResponse paginatedDistrictResponse = service.getDistrictsByFilter(filter,pageable);
        if (paginatedDistrictResponse == null) {
            return new ResponseEntity<>("DATA NOT FOUND", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(paginatedDistrictResponse, HttpStatus.OK);
    }

    @GetMapping(Routes.UPAZILA_BY_ID_ROUTE)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_MODERATOR')")
    public ResponseEntity<?> getUpazilaById(@PathVariable Long upazilaId,
                                            @RequestParam(defaultValue = "NO_FETCH") EntityFetchType fetchType) {
        Upazila upazila = service.getUpazilaById(upazilaId, fetchType);
        if (Objects.nonNull(upazilaId)) {
            return new ResponseEntity<>(upazila, HttpStatus.OK);
        } else {
            // TODO : throw EntityNotFoundException
            return new ResponseEntity<>("DATA NOT FOUND", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(Routes.UPAZILA_LIST_ROUTE)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_MODERATOR')")
    public ResponseEntity<?> getAllUpazilasByFilter(GeoFilter filter, Pageable pageable) {

        PaginatedUpazilaResponse paginatedUpazilaResponse = service.getUpazilaByFilter(filter,pageable);
        if (paginatedUpazilaResponse == null) {
            return new ResponseEntity<>("DATA NOT FOUND", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(paginatedUpazilaResponse, HttpStatus.OK);
    }
}

package com.cms.example.cms.feature.geo;

import com.cms.example.cms.common.Routes;
import com.cms.example.cms.entities.District;
import com.cms.example.cms.entities.Division;
import com.cms.example.cms.entities.Upazila;
import com.cms.example.cms.enums.EntityFetchType;
import com.cms.example.cms.dto.GeoFilterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class GeoController {

    private final GeoService service;

    @GetMapping(Routes.DIVISION_BY_ID_ROUTE)
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
    public ResponseEntity<?> getAllDivisions(GeoFilterDto filter) {

        List<Division> divisions = service.getDivisionsByFilter(filter);
        if (divisions == null || divisions.isEmpty()) {
            return new ResponseEntity<>("DATA NOT FOUND", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(divisions, HttpStatus.OK);
    }

    @GetMapping(Routes.DISTRICT_BY_ID_ROUTE)
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
    public ResponseEntity<?> getAllDistricts(GeoFilterDto filter) {

        List<District> districts = service.getDistrictsByFilter(filter);
        if (districts == null || districts.isEmpty()) {
            return new ResponseEntity<>("DATA NOT FOUND", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(districts, HttpStatus.OK);
    }

    @GetMapping(Routes.UPAZILA_BY_ID_ROUTE)
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
    public ResponseEntity<?> getAllUpazilas(GeoFilterDto filter) {

        List<Upazila> upazilas = service.getUpazilaByFilter(filter);
        if (upazilas == null || upazilas.isEmpty()) {
            return new ResponseEntity<>("DATA NOT FOUND", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(upazilas, HttpStatus.OK);
    }
}

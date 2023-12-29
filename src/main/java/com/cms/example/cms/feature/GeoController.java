package com.cms.example.cms.feature;

import com.cms.example.cms.common.Routes;
import com.cms.example.cms.entities.District;
import com.cms.example.cms.entities.Division;
import com.cms.example.cms.entities.Upazila;
import com.cms.example.cms.enums.EntityFetchType;
import com.cms.example.cms.feature.geo.districtService.DistrictService;
import com.cms.example.cms.feature.geo.divsionService.DivisionService;
import com.cms.example.cms.feature.geo.upazilaService.UpazilaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class GeoController {

    private final DivisionService divisionService;
    private final DistrictService districtService;
    private final UpazilaService upazilaService;

    @GetMapping(Routes.DIVISION_BY_ID_ROUTE)
    public ResponseEntity<?> getDivisionById(@PathVariable Long divisionId, @RequestParam(defaultValue = "NO_FETCH") EntityFetchType fetchType) {

        if (EntityFetchType.NO_FETCH.equals(fetchType)) {
            Optional<Division> division = divisionService.getDivisionById(divisionId);
            if (division.isPresent()) {
                return new ResponseEntity<>(division, HttpStatus.OK);
            }
        } else {
            Optional<Division> divisionWithDetails = divisionService.getDivisionDetailsById(divisionId);
            if (divisionWithDetails.isPresent()) {
                return new ResponseEntity<>(divisionWithDetails.get().getDistricts(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("DATA NO_FOUND", HttpStatus.NOT_FOUND);
    }

    @GetMapping(Routes.DISTRICT_BY_ID_ROUTE)
    public ResponseEntity<?> getDistrictById(@PathVariable Long districtId, @RequestParam(defaultValue = "NO_FETCH") EntityFetchType fetchType) {

        if (EntityFetchType.NO_FETCH.equals(fetchType)) {
            Optional<District> district = districtService.getDistrictById(districtId);
            if (district.isPresent()) {
                return new ResponseEntity<>(district, HttpStatus.OK);
            }
        } else {
            Optional<District> districtWithDetails = districtService.getDistrictDetailsById(districtId);
            if (districtWithDetails.isPresent()) {
                return new ResponseEntity<>(districtWithDetails.get().getUpazilas(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("DATA NO_FOUND", HttpStatus.NOT_FOUND);
    }

    @GetMapping(Routes.UPAZILA_BY_ID_ROUTE)
    public ResponseEntity<?> getUpazilaById(@PathVariable Long upazilaId) {

        Optional<Upazila> upazila = upazilaService.getUpazilaById(upazilaId);
        if (upazila.isPresent()) {
            return new ResponseEntity<>(upazila.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("DATA NO_FOUND", HttpStatus.NOT_FOUND);
    }
}

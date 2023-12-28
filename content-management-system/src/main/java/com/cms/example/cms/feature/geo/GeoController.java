package com.cms.example.cms.feature.geo;

import com.cms.example.cms.common.Routes;
import com.cms.example.cms.entities.Division;
import com.cms.example.cms.feature.responseDTO.DivisionDetailsResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
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

    @GetMapping(Routes.DIVISION_BY_ID_ROUTE)
    public ResponseEntity<?> getDivisionById(@PathVariable Long divisionId, @RequestParam(required = false) FetchType fetchType) {

        if (fetchType == null || FetchType.NO_FETCH.equals(fetchType)) {
            Optional<Division> divisionData = divisionService.getDivisionById(divisionId);
            return divisionData.map(division -> new ResponseEntity<>(division, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } else {
            Optional<Division> divisionWithDetails = divisionService.getDivisionDetailsById(divisionId);
            return divisionWithDetails.map(divisionDetails -> {
                Hibernate.initialize(divisionDetails.getDistricts());

                DivisionDetailsResponse divisionDetailsResponse = new DivisionDetailsResponse();
                divisionDetailsResponse.setDivisionId(divisionDetails.getDivisionId());
                divisionDetailsResponse.setName(divisionDetails.getName());
                divisionDetailsResponse.setNameLocal(divisionDetails.getNameLocal());
                divisionDetailsResponse.setActive(divisionDetails.getActive());
                divisionDetailsResponse.setDistricts(divisionDetails.getDistricts());
                divisionDetailsResponse.setCreatedAt(divisionDetails.getCreatedAt());
                divisionDetailsResponse.setUpdatedAt(divisionDetails.getUpdatedAt());

                return new ResponseEntity<>(divisionDetailsResponse, HttpStatus.OK);
            }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
    }
}

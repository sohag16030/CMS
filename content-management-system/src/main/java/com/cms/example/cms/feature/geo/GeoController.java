package com.cms.example.cms.feature.geo;

import com.cms.example.cms.common.Routes;
import com.cms.example.cms.entities.Division;
import com.cms.example.cms.feature.responseDTO.DivisionResponse;
import lombok.RequiredArgsConstructor;
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
    public DivisionResponse getDivisionById(@PathVariable Long divisionId, @RequestParam(required = false) FetchType fetchType) {

        if (fetchType == null || FetchType.NO_FETCH.equals(fetchType)) {
            Optional<Division> division = divisionService.getDivisionById(divisionId);
            if (division.isPresent()) {
                Division divisionData = division.get();
                DivisionResponse divisionResponse = new DivisionResponse();

                divisionResponse.setDivisionId(divisionData.getDivisionId());
                divisionResponse.setName(divisionData.getName());
                divisionResponse.setNameLocal(divisionData.getNameLocal());
                divisionResponse.setActive(divisionData.getActive());
                divisionResponse.setCreatedAt(divisionData.getCreatedAt());
                divisionResponse.setUpdatedAt(divisionData.getUpdatedAt());

                return divisionResponse;
            } else {
                return null;
            }

        } else {
            Optional<Division> divisionWithDetails = divisionService.getDivisionDetailsById(divisionId);
            if (divisionWithDetails.isPresent()) {
                Division divisionDetails = divisionWithDetails.get();
                DivisionResponse divisionDetailsResponse = new DivisionResponse();

                divisionDetailsResponse.setDivisionId(divisionDetails.getDivisionId());
                divisionDetailsResponse.setName(divisionDetails.getName());
                divisionDetailsResponse.setNameLocal(divisionDetails.getNameLocal());
                divisionDetailsResponse.setActive(divisionDetails.getActive());
                divisionDetailsResponse.setDistricts(divisionDetails.getDistricts());
                divisionDetailsResponse.setCreatedAt(divisionDetails.getCreatedAt());
                divisionDetailsResponse.setUpdatedAt(divisionDetails.getUpdatedAt());

                return divisionDetailsResponse;
            } else {
                return null;
            }
        }
    }
}

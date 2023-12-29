package com.cms.example.cms.feature.geo;

import com.cms.example.cms.common.Routes;
import com.cms.example.cms.entities.Division;
import com.cms.example.cms.enums.EntityFetchType;
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

    @GetMapping(Routes.DIVISION_BY_ID_ROUTE)
    public ResponseEntity<?> getDivisionById(@PathVariable Long divisionId, @RequestParam(defaultValue = "NO_FETCH") EntityFetchType fetchType) {

        if (EntityFetchType.NO_FETCH.equals(fetchType)) {
            Optional<Division> division = divisionService.getDivisionById(divisionId);
            if (division.isPresent()) {
                return new ResponseEntity<>(division.get(), HttpStatus.OK);
            }
        } else {
            Optional<Division> divisionWithDetails = divisionService.getDivisionDetailsById(divisionId);
            if (divisionWithDetails.isPresent()) {
                return new ResponseEntity<>(divisionWithDetails.get().getDistricts(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("DATA NO_FOUND", HttpStatus.NOT_FOUND);
    }
}

package com.cms.example.cms.feature.geo;

import com.cms.example.cms.common.Routes;
import com.cms.example.cms.entities.Division;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;


@RestController
public class GeoController {

    @Autowired
    public DivisionService divisionService;

    @GetMapping(Routes.DIVISION_BY_ID_ROUTE)
    public Division getDivisionById(@PathVariable Long divisionId, @RequestParam FetchType  fetchType) {
        if (FetchType.NO_FETCH.equals(fetchType)) {
            Optional<Division> division = divisionService.getDivisionById(divisionId);
            return division.get();
        } else {
//            Division divisionWithDetails = divisionService.getDivisionDetailsById(divisionId);
//            return new divisionWithDetails;
        }
        return null;
    }
}

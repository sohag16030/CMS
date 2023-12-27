package com.cms.example.cms.feature.geo;

import com.cms.example.cms.config.Routes;
import com.cms.example.cms.entities.Division;
import com.cms.example.cms.feature.enums.FetchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    private DivisionService divisionService;

    @GetMapping(Routes.DIVISION_BY_ID_ROUTE)
    public ResponseEntity<?> getDivisionById(@PathVariable Long divisionId, @RequestParam FetchType fetchType) {
        if (fetchType == FetchType.NO_FETCH) {
            Optional<Division> division = divisionService.getDivisionById(divisionId);
            return division.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } else {
            Division divisionWithDetails = divisionService.getDivisionDetailsById(divisionId);
            return new ResponseEntity<>(divisionWithDetails, HttpStatus.OK);
        }
    }
}

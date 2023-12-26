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

@RestController
@RequestMapping(Routes.DIVISION_BY_ID_ROUTE)
public class DivisionController {

    @Autowired
    private DivisionService divisionService;

    @GetMapping("/division/{divisionId}")
    public ResponseEntity<Division> getDivisionById(@PathVariable Long divisionId,
                                                    @RequestParam FetchType fetchType) {
        Division division;

        if (fetchType == FetchType.DETAILS) {
            division = divisionService.getDivisionById(divisionId);
        } else {
            division = divisionService.getDivisionById(divisionId);
        }

        return new ResponseEntity<>(division, HttpStatus.OK);
    }
}

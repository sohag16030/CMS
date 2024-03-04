package com.cms.example.cms.dto.listDataFilterRequestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeoFilter {

    private Long divisionId;
    private Long districtId;
    private Long upazilaId;
    private String name;
    private String nameLocal;
    private Boolean active;
}


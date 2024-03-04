package com.cms.example.cms.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeoFilterDto {

    private Long divisionId;
    private Long districtId;
    private Long upazilaId;
    private String name;
    private String nameLocal;
    private Boolean active;
}


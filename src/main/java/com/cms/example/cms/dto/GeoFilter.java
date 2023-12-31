package com.cms.example.cms.dto;

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

    public boolean isEmpty() {
        return divisionId == null && districtId == null && upazilaId == null && name == null && nameLocal == null && active == null;
    }
}


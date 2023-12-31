package com.cms.example.cms.feature.geo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Filter {

    private Long divisionId;
    private String name;
    private String nameLocal;
    private Boolean active;

    public boolean isEmpty() {
        return divisionId == null && name == null && nameLocal == null && active == null;
    }
}


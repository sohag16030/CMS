package com.cms.example.cms.feature.responseDTO;

import com.cms.example.cms.entities.District;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DivisionResponse {
    private Long divisionId;
    private String name;
    private String nameLocal;
    private Boolean active;
    private List<District> districts;
    private Date createdAt;
    private Date updatedAt;
}

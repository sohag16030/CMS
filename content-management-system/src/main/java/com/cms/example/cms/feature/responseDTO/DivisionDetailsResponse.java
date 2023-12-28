package com.cms.example.cms.feature.responseDTO;

import com.cms.example.cms.entities.District;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DivisionDetailsResponse {
    private Long divisionId;
    private String name;
    private String nameLocal;
    private Boolean active;
    private List<District> districts;
    private Date createdAt;
    private Date updatedAt;
}

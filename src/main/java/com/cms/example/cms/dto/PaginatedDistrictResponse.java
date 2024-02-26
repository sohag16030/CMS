package com.cms.example.cms.dto;

import com.cms.example.cms.entities.District;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginatedDistrictResponse {
    private List<District> districtList;
    private Long numberOfItems;
    private int numberOfPages;
}
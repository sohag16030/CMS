package com.cms.example.cms.dto.paginatedResponseDto;

import com.cms.example.cms.entities.Division;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginatedDivisionResponse {
    private List<Division> divisionList;
    private Long numberOfItems;
    private int numberOfPages;
}
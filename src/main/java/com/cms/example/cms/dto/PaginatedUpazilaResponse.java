package com.cms.example.cms.dto;

import com.cms.example.cms.entities.Upazila;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginatedUpazilaResponse {
    private List<Upazila> upazilaList;
    private Long numberOfItems;
    private int numberOfPages;
}
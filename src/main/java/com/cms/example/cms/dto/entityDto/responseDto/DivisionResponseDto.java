package com.cms.example.cms.dto.entityDto.responseDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DivisionResponseDto {
    private Long divisionId;
    private String name;
    private Boolean active;
    private List<DistrictResponseDto> districts;
}


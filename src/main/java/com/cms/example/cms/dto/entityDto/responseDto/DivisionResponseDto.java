package com.cms.example.cms.dto.entityDto.responseDto;

import com.cms.example.cms.dto.entityDto.requestDto.DistrictRequestDto;
import com.cms.example.cms.dto.entityDto.requestDto.DivisionRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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

    @JsonIgnoreProperties(value = {"division"}, allowSetters = true)
    private List<DistrictResponseDto> districts;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @JsonIgnore
    public static Boolean isNonNull(DivisionRequestDto division){
        return Objects.nonNull(division) && Objects.nonNull(division.getDivisionId());
    }
}


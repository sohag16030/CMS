package com.cms.example.cms.dto.entityDto.requestDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class DivisionRequestDto {
    private Long divisionId;
    private String name;
    private Boolean active;
    private List<DistrictRequestDto> districts;
    @JsonIgnore
    public static Boolean isNonNull(DivisionRequestDto division){
        return Objects.nonNull(division) && Objects.nonNull(division.getDivisionId());
    }
}


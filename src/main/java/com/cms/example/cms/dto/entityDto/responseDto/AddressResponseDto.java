package com.cms.example.cms.dto.entityDto.responseDto;

import com.cms.example.cms.enums.AddressType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDto {
    private Long addressId;

    private AddressType addressType;

    private Long divisionId;

    @JsonIgnoreProperties(value = {"division"}, allowSetters = true)
    private Long districtId;

    @JsonIgnoreProperties(value = {"district"}, allowSetters = true)
    private Long upazilaId;

    private Long cmsUserId;

    private Boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private DivisionResponseDto division; // DTO for Division entity

    private DistrictResponseDto district; // DTO for District entity

    private UpazilaResponseDto upazila; // DTO for Upazila entity
}


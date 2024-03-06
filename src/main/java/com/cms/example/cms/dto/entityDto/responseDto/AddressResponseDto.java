package com.cms.example.cms.dto.entityDto.responseDto;

import com.cms.example.cms.dto.entityDto.requestDto.CmsUserRequestDto;
import com.cms.example.cms.dto.entityDto.requestDto.DistrictRequestDto;
import com.cms.example.cms.dto.entityDto.requestDto.UpazilaRequestDto;
import com.cms.example.cms.entities.Division;
import com.cms.example.cms.enums.AddressType;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private DivisionResponseDto division;

    @JsonIgnoreProperties(value = {"division"}, allowSetters = true)
    private DistrictRequestDto district;

    @JsonIgnoreProperties(value = {"district"}, allowSetters = true)
    private UpazilaResponseDto upazila;

    @JsonIgnore
    private CmsUserResponseDto cmsUser;

    private Boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}


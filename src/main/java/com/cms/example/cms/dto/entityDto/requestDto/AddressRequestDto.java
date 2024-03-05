package com.cms.example.cms.dto.entityDto.requestDto;

import com.cms.example.cms.entities.Division;
import com.cms.example.cms.enums.AddressType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequestDto {
    private Long addressId;
    private AddressType addressType;
    private Division divisionId;
    private Long districtId;
    private Long upazilaId;
    private Long cmsUserId;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private DivisionRequestDto division; // DTO for Division entity
    private DistrictRequestDto district; // DTO for District entity
    private UpazilaRequestDto upazila; // DTO for Upazila entity
}


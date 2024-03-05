package com.cms.example.cms.dto.entityDto.responseDto;

import com.cms.example.cms.enums.Gender;
import com.cms.example.cms.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmsUserResponseDto {
    private Long cmsUserId;
    private String userName;
    private String roles;
    private String mobileNumber;
    private String email;
    private String name;
    private Gender gender;
    private List<AddressResponseDto> addresses; // Assuming you have an AddressDto for the Address entity
    private List<AcademicInfoResponseDto> academicInfos; // Assuming you have an AcademicInfoDto for the AcademicInfo entity
    private UserStatus userStatus;
    private Boolean isActive;
}


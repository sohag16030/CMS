package com.cms.example.cms.dto.entityDto.responseDto;

import com.cms.example.cms.dto.entityDto.requestDto.AcademicInfoRequestDto;
import com.cms.example.cms.dto.entityDto.requestDto.AddressRequestDto;
import com.cms.example.cms.enums.Gender;
import com.cms.example.cms.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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

    private List<AddressResponseDto> addresses;

    private List<AcademicInfoResponseDto> academicInfos;

    private UserStatus userStatus;

    private Boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}


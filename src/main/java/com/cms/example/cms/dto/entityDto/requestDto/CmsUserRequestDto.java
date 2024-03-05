package com.cms.example.cms.dto.entityDto.requestDto;

import com.cms.example.cms.entities.AcademicInfo;
import com.cms.example.cms.entities.Address;
import com.cms.example.cms.enums.Gender;
import com.cms.example.cms.enums.UserStatus;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CmsUserRequestDto {

    private Long cmsUserId;

    private String userName;

    private String password;

    private String roles;

    private String mobileNumber;

    private String email;

    private String name;

    private Gender gender;

    private List<AddressRequestDto> addresses;

    private List<AcademicInfoRequestDto> academicInfos;

    private UserStatus userStatus;

    private Boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}


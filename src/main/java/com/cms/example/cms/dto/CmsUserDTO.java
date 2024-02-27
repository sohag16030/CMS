package com.cms.example.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CmsUserDTO {
    private Long cmsUserId;
    private Long userContentId;
    private Long userRatingId;
    private Long addressId;
    private Long academicInfoId;
    private String mobileNumber;
    private String email;
    private String name;
    private Boolean active;
}

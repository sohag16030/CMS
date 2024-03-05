package com.cms.example.cms.dto.entityDto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentResponseDto {
    private Long contentId;
    private String title;
    private String type;
    private String path;
    private CmsUserResponseDto cmsUser; // Use CmsUserDto instead of Long cmsUserId
    private Boolean isActive;
}


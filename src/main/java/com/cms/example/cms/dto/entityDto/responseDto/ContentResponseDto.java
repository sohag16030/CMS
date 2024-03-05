package com.cms.example.cms.dto.entityDto.responseDto;

import com.cms.example.cms.dto.entityDto.requestDto.CmsUserRequestDto;
import com.cms.example.cms.dto.entityDto.requestDto.ContentRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentResponseDto {
    private Long contentId;

    private String title;

    private String type;

    private String path;

    private CmsUserResponseDto cmsUser;

    private Boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @JsonIgnore
    public static Boolean isNonNull(ContentResponseDto content){
        return Objects.nonNull(content) && Objects.nonNull(content.getContentId());
    }
}


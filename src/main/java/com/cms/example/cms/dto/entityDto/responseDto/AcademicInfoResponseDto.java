package com.cms.example.cms.dto.entityDto.responseDto;

import com.cms.example.cms.dto.entityDto.requestDto.CmsUserRequestDto;
import com.cms.example.cms.dto.entityDto.requestDto.SubjectRequestDto;
import com.cms.example.cms.enums.AcademicClass;
import com.cms.example.cms.enums.AcademicLevel;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class AcademicInfoResponseDto {

    private Long academicInfoId;

    private AcademicLevel academicLevel;

    private Double grade;

    private AcademicClass academicClass;

    private List<SubjectResponseDto> subjectResponses;

    @JsonIgnore
    private CmsUserResponseDto cmsUsers;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

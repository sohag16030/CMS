package com.cms.example.cms.dto.entityDto.responseDto;

import com.cms.example.cms.enums.AcademicClass;
import com.cms.example.cms.enums.AcademicLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<SubjectResponseDto> subjects; // Use SubjectDto instead of Long subjectIds
    private CmsUserResponseDto cmsUser; // Assuming you also have a CmsUserDto for CMS user details
}

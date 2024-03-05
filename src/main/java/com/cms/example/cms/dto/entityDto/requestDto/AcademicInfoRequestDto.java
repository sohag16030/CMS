package com.cms.example.cms.dto.entityDto.requestDto;

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
public class AcademicInfoRequestDto {
    private Long academicInfoId;
    private AcademicLevel academicLevel;
    private Double grade;
    private AcademicClass academicClass;
    private List<SubjectRequestDto> subjects; // Use SubjectDto instead of Long subjectIds
    private CmsUserRequestDto cmsUser; // Assuming you also have a CmsUserDto for CMS user details
}

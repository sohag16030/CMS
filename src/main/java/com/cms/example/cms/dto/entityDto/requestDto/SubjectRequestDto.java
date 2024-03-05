package com.cms.example.cms.dto.entityDto.requestDto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectRequestDto {
    private Long subjectId;
    private String name;
    private List<AcademicInfoRequestDto> academicInfos; // Change to list of AcademicInfoDto
}

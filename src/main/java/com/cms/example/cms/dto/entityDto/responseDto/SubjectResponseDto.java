package com.cms.example.cms.dto.entityDto.responseDto;

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
public class SubjectResponseDto {
    private Long subjectId;
    private String name;
    private List<AcademicInfoResponseDto> academicInfos; // Change to list of AcademicInfoDto
}

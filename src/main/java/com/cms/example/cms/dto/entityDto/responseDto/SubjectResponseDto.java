package com.cms.example.cms.dto.entityDto.responseDto;

import com.cms.example.cms.dto.entityDto.requestDto.AcademicInfoRequestDto;
import com.cms.example.cms.dto.entityDto.requestDto.SubjectRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectResponseDto {

    private Long subjectId;

    private String name;

    private List<AcademicInfoResponseDto> academicInfoResponseDtoList;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @JsonIgnore
    public static Boolean isNonNull(SubjectResponseDto subjectResponseDto){
        return Objects.nonNull(subjectResponseDto) && Objects.nonNull(subjectResponseDto.getSubjectId());
    }
}

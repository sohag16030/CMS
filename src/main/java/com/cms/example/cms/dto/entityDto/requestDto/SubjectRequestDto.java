package com.cms.example.cms.dto.entityDto.requestDto;

import com.cms.example.cms.entities.AcademicInfo;
import com.cms.example.cms.entities.Subject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectRequestDto {

    private Long subjectId;

    private String name;

    private List<AcademicInfoRequestDto> academicInfoRequestDtoList;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @JsonIgnore
    public static Boolean isNonNull(SubjectRequestDto subjectRequestDto){
        return Objects.nonNull(subjectRequestDto) && Objects.nonNull(subjectRequestDto.getSubjectId());
    }
}

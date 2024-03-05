package com.cms.example.cms.dto.entityDto.requestDto;

import com.cms.example.cms.entities.CmsUser;
import com.cms.example.cms.entities.Subject;
import com.cms.example.cms.enums.AcademicClass;
import com.cms.example.cms.enums.AcademicLevel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
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

    private List<SubjectRequestDto> subjects;

    @JsonIgnore
    private CmsUserRequestDto cmsUser;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

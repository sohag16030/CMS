package com.cms.example.cms.entities;

import com.cms.example.cms.enums.AcademicClass;
import com.cms.example.cms.enums.AcademicLevel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.FetchType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Entity
@Table(name = "ACADEMIC_INFO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcademicInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACADEMIC_INFO_ID")
    private Long academicInfoId;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACADEMIC_LEVEL", nullable = false)
    private AcademicLevel academicLevel;

    @Column(name = "GRADE", nullable = false)
    private Double grade;

    @Enumerated(EnumType.STRING)
    @Column(name = "CLASS", nullable = false)
    private AcademicClass academicClass;

    @ManyToMany
    @JoinTable(
            name = "ACADEMIC_INFO_SUBJECT",
            joinColumns = @JoinColumn(name = "ACADEMIC_INFO_ID"),
            inverseJoinColumns = @JoinColumn(name = "SUBJECT_ID")
    )
    private List<Subject> subjects;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CMS_USER_ID", nullable = false)
    @JsonIgnore
    private CmsUser cmsUser;

    @Column(name = "CREATED_AT", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

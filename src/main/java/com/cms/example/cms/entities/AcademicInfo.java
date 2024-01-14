package com.cms.example.cms.entities;

import com.cms.example.cms.enums.AcademicClass;
import com.cms.example.cms.enums.AcademicLevel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;
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
    @JsonIgnoreProperties(value = {"addresses","academicInfos"}, allowSetters = true)
    private CmsUser cmsUser;

    @Column(name = "CREATED_AT", nullable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    @UpdateTimestamp
    private Date updatedAt;
}

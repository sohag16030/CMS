package com.cms.example.cms.entities;

import com.cms.example.cms.entities.enums.AcademicClass;
import com.cms.example.cms.entities.enums.AcademicLevel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
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

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "LEVEL", nullable = false)
    private AcademicLevel level;

    @NotNull
    @Column(name = "GRADE", nullable = false)
    private Double grade;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "CLASS", nullable = false)
    private AcademicClass academicClass;

    @OneToMany(mappedBy = "academicInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Subject> subjects;

    @Column(name = "CREATED_AT", nullable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    @UpdateTimestamp
    private Date updatedAt;
}


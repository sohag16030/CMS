package com.cms.example.cms.entities;

import com.cms.example.cms.entities.enums.AcademicClass;
import com.cms.example.cms.entities.enums.AcademicLevel;
import jakarta.persistence.*;
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

    @ManyToMany
    @JoinTable(
            name = "ACADEMIC_INFO_SUBJECT",
            joinColumns = @JoinColumn(name = "ACADEMIC_INFO_ID"),
            inverseJoinColumns = @JoinColumn(name = "SUBJECT_ID")
    )
    private List<Subject> subjects;

    @Column(name = "CREATED_AT", nullable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    @UpdateTimestamp
    private Date updatedAt;
}

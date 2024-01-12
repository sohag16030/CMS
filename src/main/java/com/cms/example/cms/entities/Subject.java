package com.cms.example.cms.entities;

import com.cms.example.cms.feature.subject.SubjectRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
import java.util.Objects;

@Builder
@Entity
@Table(name = "SUBJECT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUBJECT_ID")
    private Long subjectId;

    @Column(name = "SUBJECT_NAME", unique = true, nullable = false)
    private String name;


    @Column(name = "NAME_LOCAL", unique = true, nullable = false)
    private String nameLocal;

    @ManyToMany(mappedBy = "subjects")
    private List<AcademicInfo> academicInfos;

    @Column(name = "CREATED_AT", nullable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    @UpdateTimestamp
    private Date updatedAt;

    @JsonIgnore
    public static Boolean isNonNull(Subject subject){
        return Objects.nonNull(subject) && Objects.nonNull(subject.getSubjectId());
    }
}

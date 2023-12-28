package com.cms.example.cms.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import com.sun.istack.NotNull;
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

    @NotNull
    @Column(name = "SUBJECT_NAME", unique = true, nullable = false)
    private String name;

    @NotNull
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
}

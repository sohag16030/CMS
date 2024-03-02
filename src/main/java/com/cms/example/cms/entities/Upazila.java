package com.cms.example.cms.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "UPAZILA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Upazila {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UPAZILA_ID")
    private Long upazilaId;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @Column(name = "NAME_LOCAL", unique = true, nullable = false)
    private String nameLocal;

    @Column(name = "ACTIVE", nullable = false)
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DISTRICT_ID", nullable = false)
    private District district;

    @Column(name = "CREATED_AT", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @JsonIgnore
    public static Boolean isNonNull(Upazila upazila){
        return Objects.nonNull(upazila) && Objects.nonNull(upazila.getUpazilaId());
    }
}


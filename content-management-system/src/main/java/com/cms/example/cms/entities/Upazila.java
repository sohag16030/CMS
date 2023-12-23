package com.cms.example.cms.entities;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

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
    private Long upozillaId;

    @NotNull
    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @NotNull
    @Column(name = "NAME_LOCAL", unique = true, nullable = false)
    private String nameLocal;

    @NotNull
    @Column(name = "ACTIVE", nullable = false)
    private Boolean active;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DISTRICT_ID", nullable = false)
    private District district;

    @Column(name = "CREATED_AT", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}


package com.cms.example.cms.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "PROFESSION")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROFESSION_ID")
    private Long professionId;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @Column(name = "NAME_LOCAL", unique = true, nullable = false)
    private String nameLocal;

    @Column(name = "INDUSTRY", nullable = false)
    private String Industry;

    @OneToOne
    @JoinColumn(name = "CMS_USER_ID")
    private CmsUser cmsUser;

    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive;

    @Column(name = "CREATED_AT", nullable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    @UpdateTimestamp
    private Date updatedAt;
}

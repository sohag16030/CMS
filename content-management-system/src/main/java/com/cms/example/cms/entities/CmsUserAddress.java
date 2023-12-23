package com.cms.example.cms.entities;

import com.cms.example.cms.entities.enums.AddressType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "CMS_USER_ADDRESS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CmsUserAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CMS_USER_ADDRESS_ID")
    private Long cmsUserAddressId;

    @NotNull
    @Column(name = "ADDRESS_TYPE", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DIVISION_ID", nullable = false)
    private Division division;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DISTRICT_ID", nullable = false)
    private District district;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UPAZILA_ID", nullable = false)
    private Upazila upazila;

    @NotNull
    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CREATED_BY", nullable = false)
    private CmsUser createdBy;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UPDATED_BY", nullable = false)
    private CmsUser updatedBy;

    @Column(name = "CREATED_AT", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}


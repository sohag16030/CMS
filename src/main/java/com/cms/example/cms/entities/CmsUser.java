package com.cms.example.cms.entities;

import com.cms.example.cms.enums.Gender;
import com.cms.example.cms.enums.UserStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
import java.util.List;

@Entity
@Table(name = "CMS_USER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CmsUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CMS_USER_ID")
    private Long cmsUserId;

    @Column(name = "MOBILE_NUMBER", unique = true, nullable = false)
    private String mobileNumber;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "GENDER", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_RATING_ID", nullable = false)
    private UserRating userRating;

    @OneToMany(mappedBy = "cmsUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Address> addresses;

    @OneToMany(mappedBy = "cmsUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AcademicInfo> academicInfos;

    @Column(name = "USER_STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive;

    @Column(name = "CREATED_AT", nullable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    @UpdateTimestamp
    private Date updatedAt;
}


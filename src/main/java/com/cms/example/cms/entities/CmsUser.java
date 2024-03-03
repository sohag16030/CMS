package com.cms.example.cms.entities;

import com.cms.example.cms.enums.Gender;
import com.cms.example.cms.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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

    @Column(name = "USER_NAME", unique = true, nullable = false)
    private String userName;

    @NotNull
    @Column(name = "PASSWORD", unique = true, nullable = false)
    private String password;

    @Column(name = "ROLES", unique = true, nullable = false)
    private String roles;


    @Column(name = "MOBILE_NUMBER", unique = true, nullable = false)
    private String mobileNumber;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "GENDER", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

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
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}


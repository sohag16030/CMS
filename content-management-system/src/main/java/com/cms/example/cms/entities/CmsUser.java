package com.cms.example.cms.entities;

import com.cms.example.cms.entities.enums.Gender;
import com.cms.example.cms.entities.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

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

    @NotNull
    @Column(name = "MOBILE_NUMBER", unique = true, nullable = false)
    private String mobileNumber;

    @Column(name = "EMAIL", unique = true, nullable = true)
    private String email;

    @NotNull
    @Column(name = "NAME", nullable = false)
    private String name;

    @NotNull
    @Column(name = "GENDER", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    @Column(name = "STATUS", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @NotNull
    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive;

    @Column(name = "CREATED_AT", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}


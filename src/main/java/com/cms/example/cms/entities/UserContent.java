package com.cms.example.cms.entities;

import com.cms.example.cms.enums.RatingType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "USER_CONTENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_CONTENT_ID")
    private Long userContentId;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "CONTENT_TYPE", nullable = false)
    private String type;

    @Lob
    @Column(name = "DATA", length = 1000)
    private byte[] data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CMS_USER_ID", nullable = false)
    @JsonIgnore
    private CmsUser cmsUser;

    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive;

    @Column(name = "CREATED_AT", nullable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    @UpdateTimestamp
    private Date updatedAt;

    @JsonIgnore
    public static Boolean isNonNull(UserContent userContent){
        return Objects.nonNull(userContent) && Objects.nonNull(userContent.getUserContentId());
    }
}

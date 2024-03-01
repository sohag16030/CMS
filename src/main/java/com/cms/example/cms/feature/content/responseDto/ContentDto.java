package com.cms.example.cms.feature.content.responseDto;

import com.cms.example.cms.entities.CmsUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentDto {
    private Long contentId;
    private String title;
    private String type;
    private String url;
    private long size;
    private CmsUser cmsUser;
    private Boolean isActive;
    private Date createdAt;
    private Date updatedAt;

}

package com.cms.example.cms.dto.entityDto.requestDto;

import com.cms.example.cms.entities.CmsUser;
import com.cms.example.cms.entities.Content;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentRequestDto {

    private Long contentId;

    private String title;

    private String type;

    private String path;

    private CmsUserRequestDto cmsUser;

    private Boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @JsonIgnore
    public static Boolean isNonNull(ContentRequestDto content){
        return Objects.nonNull(content) && Objects.nonNull(content.getContentId());
    }
}


package com.cms.example.cms.dto.entityResponseDto;

import com.cms.example.cms.entities.CmsUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContentResponse {
    private Long contentId;
    private String title;
    private String type;
    private String path;
    private CmsUser cmsUser;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

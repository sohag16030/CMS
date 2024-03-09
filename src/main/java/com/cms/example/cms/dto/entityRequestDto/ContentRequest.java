package com.cms.example.cms.dto.entityRequestDto;

import com.cms.example.cms.entities.CmsUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContentRequest {
    private Long contentId;
    private String title;
    private String type;
    private String path;
    private CmsUser cmsUser;
}

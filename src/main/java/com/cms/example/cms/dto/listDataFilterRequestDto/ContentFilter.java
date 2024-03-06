package com.cms.example.cms.dto.listDataFilterRequestDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContentFilter {
    private Long contentId;
    private String title;
    private String type;
    private String path;
    private Long cmsUserId;
    private String userName;
    private Boolean isActive;
}

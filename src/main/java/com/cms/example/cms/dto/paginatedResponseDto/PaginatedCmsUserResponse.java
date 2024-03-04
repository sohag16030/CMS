package com.cms.example.cms.dto.paginatedResponseDto;

import com.cms.example.cms.entities.CmsUser;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginatedCmsUserResponse {
    private List<CmsUser> cmsUserList;
    private Long numberOfItems;
    private int numberOfPages;
}
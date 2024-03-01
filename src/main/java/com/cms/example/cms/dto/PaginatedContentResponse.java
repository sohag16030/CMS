package com.cms.example.cms.dto;

import com.cms.example.cms.entities.Content;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginatedContentResponse {
    private List<Content> contentList;
    private Long numberOfItems;
    private int numberOfPages;
}
package com.cms.example.cms.feature.content.responseDto;

import com.cms.example.cms.entities.Content;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentUploadResponse {
    private List<Content> contents;
}

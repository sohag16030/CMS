package com.cms.example.cms.feature.content;

import com.cms.example.cms.common.Routes;

import com.cms.example.cms.entities.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;
    private final ContentRepository contentRepository;

    @PostMapping(Routes.CONTENT_UPLOAD_ROUTE)
    public ResponseEntity<?> uploadContent(@RequestParam("cmsUserId") Long cmsUserId, @RequestParam("contents") MultipartFile[] files) throws Exception {
        List<Optional<Content>> contents = new ArrayList<>();
        for (MultipartFile file : files) {
            Content content = contentService.uploadContentToFileSystem(cmsUserId, file);
            Optional<Content> getContent = contentService.getContentWithUserById(content.getContentId());
            contents.add(getContent);
        }
        return new ResponseEntity<>(contents, HttpStatus.OK);
    }

    @GetMapping(Routes.CONTENT_BY_ID_ROUTE)
    public ResponseEntity<?> getContent(@PathVariable Long contentId) {
        Optional<Content> content = contentService.getContentWithUserById(contentId);
        if (Objects.nonNull(content)) {
            return new ResponseEntity<>(content, HttpStatus.OK);
        } else {
            // TODO : throw EntityNotFoundException
            return new ResponseEntity<>("DATA NOT FOUND", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(Routes.CONTENT_UPDATE_ROUTE)
    public ResponseEntity<?> updateContent(@RequestParam("cmsUserId") Long cmsUserId, @RequestParam("contents") MultipartFile[] files) {
        try {
            List<Content> contents = new ArrayList<>();
            for (MultipartFile file : files) {
                Optional<Content> existingContent = contentRepository.getContentByTitleOfLoggedInUser(cmsUserId,file.getOriginalFilename());
                if (existingContent.isPresent()) {
                    Content contentUpdated = contentService.updateContent(cmsUserId,existingContent.get(),file);
                    contents.add(contentService.getContentWithUserById(contentUpdated.getContentId()).get());
                } else {
                    Content contentCreated = contentService.uploadContentToFileSystem(cmsUserId, file);
                    contents.add(contentService.getContentWithUserById(contentCreated.getContentId()).get());
                }
            }
            return new ResponseEntity<>(contents, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("UPDATE FAILED", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(Routes.CONTENT_LIST_ROUTE)
    public ResponseEntity getListContents(
            @RequestParam(required = false) String query,
            Pageable pageable) {
        if (query == null || query.isEmpty()) {
            return ResponseEntity.ok(contentService.getAllContents(pageable));
        }
        return ResponseEntity.ok(contentService.filterContents(query, pageable));
    }

    @GetMapping(Routes.CONTENT_DOWNLOAD_BY_ID_ROUTE)
    public ResponseEntity<byte[]> downloadContent(@PathVariable Long contentId) throws IOException {
        Optional<Content> contentData = contentRepository.findById(contentId);
        if (!contentData.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Content content = contentData.get();
        String filePath = content.getPath();
        byte[] contentBytes = Files.readAllBytes(new File(filePath).toPath());

        MediaType contentType = contentService.getContentTypeFromFileName(filePath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(contentType);
        headers.setContentDispositionFormData("attachment", content.getTitle());

        return ResponseEntity.ok()
                .headers(headers)
                .body(contentBytes);
    }

    @DeleteMapping(Routes.CONTENT_DELETE_BY_ID_ROUTE)
    public ResponseEntity<?> deleteContentById(@PathVariable Long contentId) {
        try {
            contentRepository.deleteById(contentId);
            Optional<Content> contentOptional = contentRepository.findById(contentId);
            if (!contentOptional.isPresent()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete content");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete content. No records exists with Id :: " + contentId);
        }
    }
}

package com.cms.example.cms.feature.content;

import com.cms.example.cms.common.Routes;

import com.cms.example.cms.dto.entityResponseDto.ContentResponse;
import com.cms.example.cms.dto.listDataFilterRequestDto.ContentFilter;
import com.cms.example.cms.dto.paginatedResponseDto.PaginatedContentResponse;
import com.cms.example.cms.entities.CmsUser;
import com.cms.example.cms.entities.Content;
import com.cms.example.cms.feature.user.CmsUserRepository;
import com.cms.example.cms.feature.user.CmsUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ContentController {

    private final ContentService contentService;
    private final ContentRepository contentRepository;
    private final CmsUserRepository userRepository;
    private final ModelMapper modelMapper;

    @PostMapping(Routes.CONTENT_UPLOAD_ROUTE)
    @PreAuthorize("hasAnyAuthority('ROLE_USER') or hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> uploadContent(@PathVariable Long userId, @RequestParam("contents") MultipartFile[] files) {
        List<Optional<Content>> contents = new ArrayList<>();
        for (MultipartFile file : files) {
            Content content = contentService.uploadContentToFileSystem(userId, file);
            Optional<Content> getContent = contentService.getContentWithUserById(content.getContentId());
            contents.add(getContent);
        }
        return new ResponseEntity<>(contents, HttpStatus.OK);
    }

    @GetMapping(Routes.CONTENT_BY_ID_ROUTE)
    @PreAuthorize("hasAnyAuthority('ROLE_USER') or hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getContent(@PathVariable Long contentId) {
        Optional<Content> contentOptional = contentService.getContentWithUserById(contentId);

        if (contentOptional.isPresent()) {
            Content content = contentOptional.get();
            ContentResponse response = modelMapper.map(content, ContentResponse.class);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // TODO: throw EntityNotFoundException
            return new ResponseEntity<>("DATA NOT FOUND", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(Routes.CONTENT_UPDATE_BY_ID_ROUTE)
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<?> updateContent(@PathVariable Long contentId, @RequestParam("contents") MultipartFile file) {
            try {
                Optional<Content> existingContent = Optional.of(contentRepository.getById(contentId));
                Optional<Content> contentDetails = null;
                if (existingContent.isPresent()) {
                    Content contentUpdated = contentService.updateContent(existingContent.get(), file);
                    contentDetails = contentService.getContentWithUserById(contentUpdated.getContentId());
                } else {
                    // TODO : throw EntityNotFoundException
                    return new ResponseEntity<>("DATA NOT FOUND", HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(contentDetails, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("UPDATE FAILED", HttpStatus.NOT_FOUND);
            }
    }

    @GetMapping(Routes.CONTENT_LIST_ROUTE_BY_USER)
    @PreAuthorize("hasAnyAuthority('ROLE_USER') or hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getListContentsByUser(ContentFilter filter, Pageable pageable) {
        PaginatedContentResponse paginatedCmsUserResponse = null;
        paginatedCmsUserResponse = contentService.getAllContentWithFilter(filter, pageable);

        if (paginatedCmsUserResponse == null) {
            return new ResponseEntity<>("DATA NOT FOUND", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(paginatedCmsUserResponse, HttpStatus.OK);
    }

    @GetMapping(Routes.CONTENT_LIST_ROUTE)
    @PreAuthorize("hasAnyAuthority('ROLE_USER') or hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getListContents(ContentFilter filter, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        CmsUser cmsUser = userRepository.getByUserName(username);
        String roles = authentication.getAuthorities().toString();
        PaginatedContentResponse paginatedCmsUserResponse = null;

        if (roles.contains("ROLE_ADMIN")) {
            paginatedCmsUserResponse = contentService.getAllContentWithFilter(filter, pageable);
        } else {
            filter.setCmsUserId(cmsUser.getCmsUserId());
            paginatedCmsUserResponse = contentService.getAllContentWithFilter(filter, pageable);
        }

        if (paginatedCmsUserResponse == null) {
            return new ResponseEntity<>("DATA NOT FOUND", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(paginatedCmsUserResponse, HttpStatus.OK);
    }

    @GetMapping(Routes.CONTENT_DOWNLOAD_BY_ID_ROUTE)
    @PreAuthorize("hasAnyAuthority('ROLE_USER') or hasAnyAuthority('ROLE_ADMIN')")
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
        headers.setContentDisposition(
                ContentDisposition.attachment().filename(content.getTitle()).build()
        );
        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition");

        return ResponseEntity.ok()
                .headers(headers)
                .body(contentBytes);
    }

    @DeleteMapping(Routes.CONTENT_DELETE_BY_ID_ROUTE)
    @PreAuthorize("hasAnyAuthority('ROLE_USER') or hasAnyAuthority('ROLE_ADMIN')")
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

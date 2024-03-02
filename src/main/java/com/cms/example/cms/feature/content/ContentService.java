package com.cms.example.cms.feature.content;

import com.cms.example.cms.dto.PaginatedContentResponse;
import com.cms.example.cms.entities.Content;
import com.cms.example.cms.feature.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContentService {

    private static final String UPLOAD_DIR = "J:\\Content";

    private final ContentRepository contentRepository;
    private final UserRepository userRepository;

    public Content uploadContentToFileSystem(MultipartFile file, Long cmsUserId) throws Exception {
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String filePath = UPLOAD_DIR + File.separator + file.getOriginalFilename();
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Content content = contentRepository.save(Content.builder()
                .title(file.getOriginalFilename())
                .type(file.getContentType())
                .path(filePath)
                .cmsUser(userRepository.getOne(cmsUserId))
                .isActive(true)
                .build());

        if (content != null) return content;
        return null;
    }

    public MediaType getContentTypeFromFileName(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        switch (extension.toLowerCase()) {
            case "pdf":
                return MediaType.APPLICATION_PDF;
            case "xlsx":
                return MediaType.APPLICATION_OCTET_STREAM;
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            // Add more cases for other file types as needed
            default:
                return MediaType.APPLICATION_OCTET_STREAM; // Default to binary data
        }
    }

    public Optional<Content> getContentWithUserById(Long contentId) {
        Optional<Content> content = contentRepository.findByIdWithDetails(contentId);
        return content;
    }

    public PaginatedContentResponse getAllContents(Pageable pageable) {
        Page<Content> contents = contentRepository.findAll(pageable);
        return PaginatedContentResponse.builder()
                .numberOfItems(contents.getTotalElements()).numberOfPages(contents.getTotalPages())
                .contentList(contents.getContent())
                .build();
    }

    public PaginatedContentResponse filterContents(String title, Pageable pageable) {
        Page<Content> contents = contentRepository.findByTitleContaining(title, pageable);
        return PaginatedContentResponse.builder()
                .numberOfItems(contents.getTotalElements()).numberOfPages(contents.getTotalPages())
                .contentList(contents.getContent())
                .build();
    }
}

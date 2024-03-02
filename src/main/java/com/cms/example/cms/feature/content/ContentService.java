package com.cms.example.cms.feature.content;

import com.cms.example.cms.dto.PaginatedContentResponse;
import com.cms.example.cms.entities.Content;
import com.cms.example.cms.feature.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContentService {

    private static final String UPLOAD_DIR = "J:\\Content\\";

    private final ContentRepository contentRepository;
    private final UserRepository userRepository;

    public Content uploadContentToFileSystem(Long cmsUserId, MultipartFile file) {
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String userIdWithTimestamp = getUserIdWithTimestamp(cmsUserId);

        String fileNameWithoutExtension = getNameWithoutExtension(file.getOriginalFilename());
        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        StringBuilder filePathBuilder = new StringBuilder();
        filePathBuilder
                .append(UPLOAD_DIR)
                .append(fileNameWithoutExtension)
                .append(userIdWithTimestamp)
                .append(extension);

        StringBuilder rename = new StringBuilder();
        rename
                .append(fileNameWithoutExtension)
                .append(userIdWithTimestamp)
                .append(extension);

        String filePath = filePathBuilder.toString();
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contentRepository.save(Content.builder()
                .title(rename.toString())
                .type(file.getContentType())
                .path(filePath)
                .cmsUser(userRepository.getOne(cmsUserId))
                .isActive(true)
                .build());
    }

    public static String getNameWithoutExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
    }

    private String getUserIdWithTimestamp(Long cmsUserId) {
        LocalDateTime timestamp = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String formattedTimestamp = timestamp.format(formatter);

        String userIdWithTimestamp = "_" + cmsUserId + "_" + formattedTimestamp + ".";
        return userIdWithTimestamp;
    }

    public Content updateContent(Long cmsUserId, Content existingContent, MultipartFile file) {
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
        existingContent.setPath(filePath);
        return existingContent;
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

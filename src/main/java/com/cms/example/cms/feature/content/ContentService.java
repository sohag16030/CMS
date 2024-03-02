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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContentService {

    private static final String UPLOAD_DIR = "J:\\Content\\";

    private final ContentRepository contentRepository;
    private final UserRepository userRepository;

    public Content uploadContentToFileSystem(Long cmsUserId, MultipartFile file) {
        initiateDirectory();
        StringBuilder filePathBuilder = getFilePathBuilder(cmsUserId, file);
        StringBuilder rename = getTitle(cmsUserId, file);
        fileStorageSave(file, filePathBuilder);

        return contentRepository.save(Content.builder()
                .title(rename.toString())
                .type(file.getContentType())
                .path(filePathBuilder.toString())
                .cmsUser(userRepository.getOne(cmsUserId))
                .isActive(true)
                .build());
    }

    public Content updateContent(Content existingContent, MultipartFile file) {
        initiateDirectory();
        StringBuilder filePathBuilder = getFilePathBuilder(existingContent.getCmsUser().getCmsUserId(), file);
        StringBuilder rename = getTitle(existingContent.getCmsUser().getCmsUserId(), file);
        fileStorageSave(file, filePathBuilder);

        existingContent.setContentId(existingContent.getContentId());
        existingContent.setPath(filePathBuilder.toString());
        existingContent.setTitle(rename.toString());

        contentRepository.save(existingContent);

        return existingContent;
    }

    private void initiateDirectory() {
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private StringBuilder getFilePathBuilder(Long cmsUserId, MultipartFile file) {

        String userIdWithTimestamp = getUserIdWithTimestamp(cmsUserId);
        String fileNameWithoutExtension = getNameWithoutExtension(file.getOriginalFilename());
        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        StringBuilder filePathBuilder = new StringBuilder();
        filePathBuilder
                .append(UPLOAD_DIR)
                .append(fileNameWithoutExtension)
                .append(userIdWithTimestamp)
                .append(extension);
        return filePathBuilder;
    }

    private StringBuilder getTitle(Long cmsUserId, MultipartFile file) {

        String userIdWithTimestamp = getUserIdWithTimestamp(cmsUserId);
        String fileNameWithoutExtension = getNameWithoutExtension(file.getOriginalFilename());
        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        StringBuilder rename = new StringBuilder();
        rename
                .append(fileNameWithoutExtension)
                .append(userIdWithTimestamp)
                .append(extension);
        return rename;
    }

    private void fileStorageSave(MultipartFile file, StringBuilder filePathBuilder) {
        try {
            file.transferTo(new File(filePathBuilder.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getNameWithoutExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
    }

    private String getUserIdWithTimestamp(Long cmsUserId) {
        LocalDateTime timestamp = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String formattedTimestamp = timestamp.format(formatter);

        StringBuilder userIdWithTimestampBuilder = new StringBuilder();
        userIdWithTimestampBuilder.append("_");
        userIdWithTimestampBuilder.append(formattedTimestamp);
        userIdWithTimestampBuilder.append("_");
        userIdWithTimestampBuilder.append(cmsUserId);
        userIdWithTimestampBuilder.append(".");

        return userIdWithTimestampBuilder.toString();
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

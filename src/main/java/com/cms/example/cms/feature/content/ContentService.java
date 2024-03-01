package com.cms.example.cms.feature.content;

import com.cms.example.cms.dto.PaginatedCmsUserResponse;
import com.cms.example.cms.dto.PaginatedContentResponse;
import com.cms.example.cms.entities.CmsUser;
import com.cms.example.cms.entities.Content;
import com.cms.example.cms.feature.content.responseDto.ContentUploadResponse;
import com.cms.example.cms.feature.user.UserRepository;
import com.cms.example.cms.feature.content.exception.ContentStorageException;
import com.cms.example.cms.feature.content.exception.ContentsNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContentService {

    private static final String UPLOAD_DIR = "J:\\Content";

    private final ContentRepository contentRepository;
    private final UserRepository userRepository;

//    public ContentUploadResponse uploadContent(MultipartFile file, Long cmsUserId) {
//        try {
//            store(file,cmsUserId);
//            return new ContentUploadResponse("SUCCESS", file.getOriginalFilename());
//        } catch (Exception e) {
//            return new ContentUploadResponse("FAILED", file.getOriginalFilename());
//        }
//    }

    //    public Content store(MultipartFile file, Long cmsUserId) throws IOException {
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        try {
//            Content userContent = new Content();
//            userContent.setTitle(fileName);
//            userContent.setType(file.getContentType());
//            userContent.setData(file.getBytes());
//            userContent.setIsActive(true);
//            userContent.setCmsUser(userRepository.getOne(cmsUserId));
//
//            return contentRepository.save(userContent);
//        } catch (IOException ex) {
//            throw new ContentStorageException("Could not store file " + fileName + ". Please try again!", ex);
//        }
//    }
    public String uploadContentToFileSystem(MultipartFile file, Long cmsUserId) throws Exception {
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

        if (content != null) return "Content upload successful" + filePath;
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

//    public Content getFile(Long id) {
//        try {
//            return contentRepository.findById(id).get();
//        } catch (ContentsNotFoundException ex) {
//            throw new ContentsNotFoundException("File not found id ::" + id, ex);
//        }
//
//    }

//    public Stream<Content> getAllFiles() {
//        return contentRepository.findAll().stream();
//    }
//
    public Optional<Content> getContentWithUserById(Long contentId) {
        Optional<Content> content = contentRepository.findByIdWithDetails(contentId);
        return content;
    }
//
//    public PaginatedContentResponse getAllContents(Pageable pageable) {
//        Page<Content> contents = contentRepository.findAll(pageable);
//        return PaginatedContentResponse.builder()
//                .numberOfItems(contents.getTotalElements()).numberOfPages(contents.getTotalPages())
//                .contentList(contents.getContent())
//                .build();
//    }
//
//    public PaginatedContentResponse filterContents(String title, Pageable pageable) {
//        Page<Content> contents = contentRepository.findByTitleContaining(title, pageable);
//        return PaginatedContentResponse.builder()
//                .numberOfItems(contents.getTotalElements()).numberOfPages(contents.getTotalPages())
//                .contentList(contents.getContent())
//                .build();
//    }
}

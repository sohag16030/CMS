package com.cms.example.cms.feature.userContent;

import com.cms.example.cms.entities.UserContent;
import com.cms.example.cms.feature.user.UserRepository;
import com.cms.example.cms.feature.userContent.exception.ContentStorageException;
import com.cms.example.cms.feature.userContent.exception.ContentsNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class ContentUploadService {

    @Autowired
    private UserContentRepository fileDBRepository;
    @Autowired
    private UserRepository userRepository;

    public ContentUploadResponse uploadFile( MultipartFile file,Long cmsUserId) {
        try {
            store(file,cmsUserId);
            return new ContentUploadResponse("SUCCESS", file.getOriginalFilename());
        } catch (Exception e) {
            return new ContentUploadResponse("FAILED", file.getOriginalFilename());
        }
    }

    public UserContent store(MultipartFile file,Long cmsUserId) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            UserContent userContent = new UserContent();
            userContent.setTitle(fileName);
            userContent.setType(file.getContentType());
            userContent.setData(file.getBytes());
            userContent.setIsActive(true);
            userContent.setCmsUser(userRepository.getOne(cmsUserId));

            return fileDBRepository.save(userContent);
        } catch (IOException ex) {
            throw new ContentStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public UserContent getFile(Long id) {
        try {
            return fileDBRepository.findById(id).get();
        } catch (ContentsNotFoundException ex) {
            throw new ContentsNotFoundException("File not found id ::" + id, ex);
        }

    }

    public Stream<UserContent> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }
}

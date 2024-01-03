package com.cms.example.cms.feature.cmsUser;

import com.cms.example.cms.entities.CmsUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CmsUserService {

    private final CmsUserRepository cmsUserRepository;

    public List<CmsUser> getAllCmsUsers() {
        return cmsUserRepository.findAll();
    }

    public Optional<CmsUser> getCmsUserById(Long id) {
        return cmsUserRepository.findById(id);
    }

    public CmsUser saveCmsUser(CmsUser cmsUser) {
        return cmsUserRepository.save(cmsUser);
    }

    public void deleteCmsUser(Long id) {
        cmsUserRepository.deleteById(id);
    }
}

package com.cms.example.cms.feature.user;

import com.cms.example.cms.entities.CmsUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CmsUserService {

    private final CmsUserRepository cmsUserRepository;

    public List<CmsUser> getAllCmsUsers() {
        return cmsUserRepository.findAll();
    }

    public Optional<CmsUser> getCmsUserById(Long id) {
        return cmsUserRepository.findById(id);
    }

    @Transactional
    public CmsUser saveCmsUser(CmsUser cmsUser) {
        if (cmsUser.getAddresses() != null) {
            cmsUser.getAddresses().forEach(address -> address.setCmsUser(cmsUser));
        }

        if (cmsUser.getAcademicInfos() != null) {
            cmsUser.getAcademicInfos().forEach(academicInfo -> academicInfo.setCmsUser(cmsUser));
        }
        return cmsUserRepository.save(cmsUser);
    }

    public void deleteCmsUser(Long id) {
        cmsUserRepository.deleteById(id);
    }
}

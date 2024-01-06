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
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public CmsUser saveCmsUser(CmsUser cmsUser) {
//        if (cmsUser.getAddresses() != null) {
//            cmsUser.getAddresses().forEach(address -> address.setCmsUser(cmsUser));
//        }
//
//        if (cmsUser.getAcademicInfos() != null) {
//            cmsUser.getAcademicInfos().forEach(academicInfo -> academicInfo.setCmsUser(cmsUser));
//        }
        return userRepository.save(cmsUser);
    }

}

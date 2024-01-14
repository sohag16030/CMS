package com.cms.example.cms.feature.user;

import com.cms.example.cms.entities.AcademicInfo;
import com.cms.example.cms.entities.Address;
import com.cms.example.cms.entities.CmsUser;
import com.cms.example.cms.entities.District;
import com.cms.example.cms.entities.Division;
import com.cms.example.cms.entities.Subject;
import com.cms.example.cms.entities.Upazila;
import com.cms.example.cms.entities.UserRating;
import com.cms.example.cms.feature.geo.DistrictRepository;
import com.cms.example.cms.feature.geo.DivisionRepository;
import com.cms.example.cms.feature.geo.UpazilaRepository;
import com.cms.example.cms.feature.subject.SubjectRepository;
import com.cms.example.cms.feature.userRating.UserRatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserRatingRepository userRatingRepository;
    private final DivisionRepository divisionRepository;
    private final DistrictRepository districtRepository;
    private final UpazilaRepository upazilaRepository;
    private final SubjectRepository subjectRepository;

    @Transactional
    public CmsUser saveCmsUser(CmsUser cmsUser) {
        if (UserRating.isNonNull(cmsUser.getUserRating())) {
            cmsUser.setUserRating(userRatingRepository.getOne(cmsUser.getUserRating().getUserRatingId()));
        }
        populateAddress(cmsUser);
        populateAcademicInfo(cmsUser);
        return userRepository.save(cmsUser);
    }

    private void populateAcademicInfo(CmsUser cmsUser) {
        if (CollectionUtils.isEmpty(cmsUser.getAcademicInfos())) return;
        cmsUser.getAcademicInfos().forEach(academicInfo -> {
            populateSubject(academicInfo);
            academicInfo.setCmsUser(cmsUser);
        });
    }

    private void populateSubject(AcademicInfo academicInfo) {
        List<Subject> subjectList = new ArrayList<>();
        academicInfo.getSubjects().forEach(subject -> {
            if (Subject.isNonNull(subject)) {
                Subject retrievedSubject = subjectRepository.getOne(subject.getSubjectId());
                subjectList.add(retrievedSubject);
            }
        });
        academicInfo.setSubjects(subjectList);
    }

    private void populateAddress(CmsUser cmsUser) {
        if (CollectionUtils.isEmpty(cmsUser.getAddresses())) return;
        cmsUser.getAddresses().forEach(address -> {
            if (Division.isNonNull(address.getDivision())) {
                address.setDivision(divisionRepository.getOne(address.getDivision().getDivisionId()));
            }

            if (District.isNonNull(address.getDistrict())) {
                address.setDistrict(districtRepository.getOne(address.getDistrict().getDistrictId()));
            }

            if (Upazila.isNonNull(address.getUpazila())) {
                address.setUpazila(upazilaRepository.getOne(address.getUpazila().getUpazilaId()));
            }
            address.setCmsUser(cmsUser);
        });
    }

    public CmsUser getCmsUserById(Long cmsUserId) {
        Optional<CmsUser> optionalCmsUser = null;
        optionalCmsUser = userRepository.findById(cmsUserId);

        userRepository.fetchRatingInfoByRatingId(optionalCmsUser.get().getCmsUserId(),optionalCmsUser.get().getUserRating().getUserRatingId());
        userRepository.fetchAddressInfoByUserId(optionalCmsUser.get().getCmsUserId());
        userRepository.fetchAcademicInfoByUserId(optionalCmsUser.get().getCmsUserId());

        if (optionalCmsUser.isPresent()) {
            return optionalCmsUser.get();
        } else return null;
    }

}

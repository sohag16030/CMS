package com.cms.example.cms.feature.user;

import com.cms.example.cms.dto.paginatedResponseDto.PaginatedCmsUserResponse;
import com.cms.example.cms.dto.listDataFilterRequestDto.CmsUserFilter;
import com.cms.example.cms.entities.AcademicInfo;
import com.cms.example.cms.entities.Address;
import com.cms.example.cms.entities.CmsUser;
import com.cms.example.cms.entities.District;
import com.cms.example.cms.entities.Division;
import com.cms.example.cms.entities.Subject;
import com.cms.example.cms.entities.Upazila;
import com.cms.example.cms.feature.academicInfo.AcademicInfoRepository;
import com.cms.example.cms.feature.address.AddressRepository;
import com.cms.example.cms.feature.geo.DistrictRepository;
import com.cms.example.cms.feature.geo.DivisionRepository;
import com.cms.example.cms.feature.geo.UpazilaRepository;
import com.cms.example.cms.feature.subject.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CmsUserService {

    private final CmsUserRepository userRepository;
    private final DivisionRepository divisionRepository;
    private final DistrictRepository districtRepository;
    private final UpazilaRepository upazilaRepository;
    private final SubjectRepository subjectRepository;
    private final AcademicInfoRepository academicInfoRepository;
    private final AddressRepository addressRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public static final String DEFAULT_ROLE = "ROLE_USER";

    @Transactional
    public CmsUser saveCmsUser(CmsUser cmsUser) {
        cmsUser.setRoles(DEFAULT_ROLE);
//      cmsUser.setPassword(cmsUser.getPassword());
        cmsUser.setPassword(passwordEncoder.encode(cmsUser.getPassword()));
        cmsUser.setIsActive(true);
        cmsUser = userRepository.save(cmsUser);
        return cmsUser;
        // return getCmsUserById(cmsUser.getCmsUserId());
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
        academicInfo.getSubjects().clear();
        academicInfo.setSubjects(subjectList);
    }
    public CmsUser getCmsUserById(Long cmsUserId) {
        Optional<CmsUser> optionalCmsUser = userRepository.findById(cmsUserId);
        return optionalCmsUser.orElse(null);
    }

    public CmsUser getCmsUserDetailsById(Long cmsUserId) {

        Optional<CmsUser> optionalCmsUser = userRepository.findById(cmsUserId);

        //addresses load
         userRepository.fetchUserAddressInfoByUserId(cmsUserId);

        //academic info loading
        CmsUser cmsUsers = userRepository.fetchAcademicInfoByUserId(cmsUserId);
        if (Objects.nonNull(cmsUsers)) {
            List<Long> academicInfoIds = cmsUsers.getAcademicInfos().stream().map(AcademicInfo::getAcademicInfoId).collect(Collectors.toList());
            if (!academicInfoIds.isEmpty()) academicInfoRepository.fetchSubjectsByAcademicInfoIdIn(academicInfoIds);
        }

        return optionalCmsUser.orElse(null);
    }

    @Transactional
    public CmsUser updateCmsUser(Long cmsUserId, CmsUser sourceUser) {
        CmsUser existingUser = userRepository.findById(cmsUserId).orElse(null);
        if (existingUser == null) {
            throw new RuntimeException("User not found");
        }
        // Create a new object to hold only the properties want to copy
        existingUser.setUserName(sourceUser.getUserName());
        existingUser.setMobileNumber(sourceUser.getMobileNumber());
        existingUser.setEmail(sourceUser.getEmail());
        existingUser.setName(sourceUser.getName());
        existingUser.setGender(sourceUser.getGender());
        existingUser.setIsActive(true);

        //Update academicInfos
        //updateAcademicInfos(existingUser, sourceUser);

        return getCmsUserById(cmsUserId);
    }
    private void updateAcademicInfos(CmsUser existingUser, CmsUser sourceUser) {
        List<AcademicInfo> updatedAcademicInfos = new ArrayList<>();

        for (AcademicInfo updatedAcademicInfo : sourceUser.getAcademicInfos()) {
            AcademicInfo existingAcademicInfo = existingUser.getAcademicInfos()
                    .stream()
                    .filter(info -> info.getAcademicInfoId().equals(updatedAcademicInfo.getAcademicInfoId()))
                    .findFirst()
                    .orElse(null);

            if (existingAcademicInfo != null) {
                // modify existing academic info
                AcademicInfo modifiedExistingAcademicInfo = modifyExistingAcademicInfo(updatedAcademicInfo, existingAcademicInfo);
                updatedAcademicInfos.add(modifiedExistingAcademicInfo);
            } else {
                //need to add new academic info
                populateSubject(updatedAcademicInfo);
                updatedAcademicInfo.setCmsUser(existingUser);
                updatedAcademicInfos.add(updatedAcademicInfo);
            }
        }
        existingUser.getAcademicInfos().clear();
        existingUser.setAcademicInfos(updatedAcademicInfos);
    }

    private AcademicInfo modifyExistingAcademicInfo(AcademicInfo updatedAcademicInfo, AcademicInfo existingAcademicInfo) {
        existingAcademicInfo.setAcademicLevel(updatedAcademicInfo.getAcademicLevel());
        existingAcademicInfo.setGrade(updatedAcademicInfo.getGrade());
        existingAcademicInfo.setAcademicClass(updatedAcademicInfo.getAcademicClass());

        // Update subjects
        List<Subject> updatedSubjects = new ArrayList<>();
        for (Subject updatedSubject : updatedAcademicInfo.getSubjects()) {
            Subject existingSubject = subjectRepository.findById(updatedSubject.getSubjectId()).orElse(null);
            if (existingSubject != null) {
                updatedSubjects.add(subjectRepository.getOne(updatedSubject.getSubjectId()));
            }
        }
        existingAcademicInfo.getSubjects().clear();
        existingAcademicInfo.setSubjects(updatedSubjects);
        return existingAcademicInfo;
    }

    public PaginatedCmsUserResponse getAllUsersWithFilter(CmsUserFilter filter, Pageable pageable) {
        Page<CmsUser> cmsUsers = userRepository.search(filter.getEmail(), pageable);
        return PaginatedCmsUserResponse.builder()
                .numberOfItems(cmsUsers.getTotalElements()).numberOfPages(cmsUsers.getTotalPages())
                .cmsUserList(cmsUsers.getContent())
                .build();
    }

    public CmsUser getLoggedInUser(Principal principal) {
        Optional<CmsUser> user = userRepository.findByUserName(principal.getName());
        return user.orElse(null);
    }

    public boolean loggedInUser(Principal principal, Long userId) {
        CmsUser loggedInUser = getLoggedInUser(principal);
        return loggedInUser.getCmsUserId().equals(userId);
    }
//    public boolean principalHasAdminRole(Principal principal) {
//        if (principal instanceof Authentication) {
//            Authentication authentication = (Authentication) principal;
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            return userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        }
//        return false;
//    }
}

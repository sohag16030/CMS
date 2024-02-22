package com.cms.example.cms.feature.user;

import com.cms.example.cms.entities.AcademicInfo;
import com.cms.example.cms.entities.Address;
import com.cms.example.cms.entities.CmsUser;
import com.cms.example.cms.entities.District;
import com.cms.example.cms.entities.Division;
import com.cms.example.cms.entities.Subject;
import com.cms.example.cms.entities.Upazila;
import com.cms.example.cms.entities.UserRating;
import com.cms.example.cms.feature.academicInfo.AcademicInfoRepository;
import com.cms.example.cms.feature.address.AddressRepository;
import com.cms.example.cms.feature.geo.DistrictRepository;
import com.cms.example.cms.feature.geo.DivisionRepository;
import com.cms.example.cms.feature.geo.UpazilaRepository;
import com.cms.example.cms.feature.subject.SubjectRepository;
import com.cms.example.cms.feature.userRating.UserRatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
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
    private final AcademicInfoRepository academicInfoRepository;
    private final AddressRepository addressRepository;

    @Transactional
    public CmsUser saveCmsUser(CmsUser cmsUser) {
        if (UserRating.isNonNull(cmsUser.getUserRating())) {
            cmsUser.setUserRating(userRatingRepository.getOne(cmsUser.getUserRating().getUserRatingId()));
        }
        populateAddress(cmsUser);
        populateAcademicInfo(cmsUser);
        cmsUser = userRepository.save(cmsUser);
        return getCmsUserById(cmsUser.getCmsUserId());
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
        Optional<CmsUser> optionalCmsUser = null;
        optionalCmsUser = userRepository.fetchUserRatingWithAddressInfoByUserId(cmsUserId);
        CmsUser cmsUsers = userRepository.fetchAcademicInfoByUserId(optionalCmsUser.get().getCmsUserId());

        List<Long> academicInfoIds = cmsUsers.getAcademicInfos().stream().map(AcademicInfo::getAcademicInfoId).collect(Collectors.toList());
        academicInfoRepository.fetchSubjectsByAcademicInfoIdIn(academicInfoIds);

        if (optionalCmsUser.isPresent()) {
            return optionalCmsUser.get();
        } else return null;
    }

    @Transactional
    public CmsUser updateCmsUser(Long cmsUserId, CmsUser sourceUser) {
        CmsUser existingUser = userRepository.findById(cmsUserId).orElse(null);
        if (existingUser == null) {
            throw new RuntimeException("User not found");
        }
        // Create a new object to hold only the properties want to copy
        UserPropertiesToCopy propertiesToCopy = new UserPropertiesToCopy();
        propertiesToCopy.setName(sourceUser.getName());
        propertiesToCopy.setGender(sourceUser.getGender());
        propertiesToCopy.setUserRating(sourceUser.getUserRating());
        propertiesToCopy.setUserStatus(sourceUser.getUserStatus());
        propertiesToCopy.setIsActive(sourceUser.getIsActive());

        // Copy properties from the filtered object to the existingUser
        BeanUtils.copyProperties(propertiesToCopy, existingUser);

        // Update addresses

        List<Long> addressIds = sourceUser.getAddresses().stream().map(Address::getAddressId).collect(Collectors.toList());
        List<Address> getAddressesInfoByIds = addressRepository.fetchAddressesInfoByAddressIdsIn(addressIds);

        Map<Long, Address> addressesMap = getAddressesInfoByIds.stream()
                .collect(Collectors.toMap(Address::getAddressId, Function.identity()));

        List<Address> updatedAddresses = new ArrayList<>();
        List<Address> updatedAddressesSource = sourceUser.getAddresses();

        for (Address updatedAddressSource : updatedAddressesSource) {
            Long addressId = updatedAddressSource.getAddressId();
            if(addressesMap.containsKey(addressId)){
                Address modifiedAddress = addressesMap.get(addressId);
               //update address
                modifiedAddress.setAddressId(addressId);
                modifiedAddress.setAddressType(updatedAddressSource.getAddressType());
                if (updatedAddressSource.getDivision() != null) {
                    modifiedAddress.setDivision(divisionRepository.getOne(updatedAddressSource.getDivision().getDivisionId()));
                }
                if (updatedAddressSource.getDistrict() != null) {
                    modifiedAddress.setDistrict(districtRepository.getOne(updatedAddressSource.getDistrict().getDistrictId()));
                }
                if (updatedAddressSource.getUpazila() != null) {
                    modifiedAddress.setUpazila(upazilaRepository.getOne(updatedAddressSource.getUpazila().getUpazilaId()));
                }
                modifiedAddress.setIsActive(updatedAddressSource.getIsActive());
                updatedAddresses.add(modifiedAddress);
            }
        }
        existingUser.getAddresses().clear();
        existingUser.setAddresses(updatedAddresses);
        
        // Update academicInfos
        List<AcademicInfo> updatedAcademicInfos = new ArrayList<>();

        for (AcademicInfo updatedAcademicInfo : sourceUser.getAcademicInfos()) {
            AcademicInfo existingAcademicInfo = existingUser.getAcademicInfos()
                    .stream()
                    .filter(info -> info.getAcademicInfoId().equals(updatedAcademicInfo.getAcademicInfoId()))
                    .findFirst()
                    .orElse(null);

            if (existingAcademicInfo != null) {
                existingAcademicInfo.setAcademicLevel(updatedAcademicInfo.getAcademicLevel());
                existingAcademicInfo.setGrade(updatedAcademicInfo.getGrade());
                existingAcademicInfo.setAcademicClass(updatedAcademicInfo.getAcademicClass());

                // Update subjects
                List<Subject> updatedSubjects = new ArrayList<>();
                for (Subject updatedSubject : updatedAcademicInfo.getSubjects()) {
                    Subject existingSubject = subjectRepository.findById(updatedSubject.getSubjectId()).orElse(null);
                    if (existingSubject != null) {
                        updatedSubjects.add(subjectRepository.getOne(updatedSubject.getSubjectId()));
                    } else {
                        //need to add new subject
                    }
                }
                existingAcademicInfo.getSubjects().clear();
                existingAcademicInfo.setSubjects(updatedSubjects);
                updatedAcademicInfos.add(existingAcademicInfo);
            } else {
                //need to add new academic info
                updatedAcademicInfos.add(updatedAcademicInfo);
            }
        }
        existingUser.setAcademicInfos(updatedAcademicInfos);

        // Save the updated user
        CmsUser updatedCmsUser = userRepository.save(existingUser);
        return getCmsUserById(updatedCmsUser.getCmsUserId());
    }

}

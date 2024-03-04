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

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        cmsUser.setPassword(passwordEncoder.encode(cmsUser.getPassword()));
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
        Optional<CmsUser> optionalCmsUser = userRepository.fetchUserAddressInfoByUserId(cmsUserId);
        CmsUser cmsUsers = userRepository.fetchAcademicInfoByUserId(cmsUserId);

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
        existingUser.setUserName(sourceUser.getUserName());
        existingUser.setMobileNumber(sourceUser.getMobileNumber());
        existingUser.setEmail(sourceUser.getEmail());
        existingUser.setName(sourceUser.getName());
        existingUser.setGender(sourceUser.getGender());
        existingUser.setUserStatus(sourceUser.getUserStatus());
        existingUser.setIsActive(sourceUser.getIsActive());

        //BeanUtils.copyProperties(sourceUser,existingUser);

        // Update addresses
        updateAddresses(existingUser, sourceUser);

        // Update academicInfos
        updateAcademicInfos(existingUser, sourceUser);

        return getCmsUserById(cmsUserId);
    }

    private void updateAddresses(CmsUser existingUser, CmsUser sourceUser) {
        List<Long> addressIds = sourceUser.getAddresses().stream().map(Address::getAddressId).collect(Collectors.toList());
        List<Address> getAddressesInfoByIds = addressRepository.fetchAddressesInfoByAddressIdsIn(addressIds);

        Map<Long, Address> addressesMap = getAddressesInfoByIds.stream()
                .collect(Collectors.toMap(Address::getAddressId, Function.identity()));

        List<Address> updatedAddresses = new ArrayList<>();

        for (Address updatedAddressSource : sourceUser.getAddresses()) {
            Long addressId = updatedAddressSource.getAddressId();
            if (addressesMap.containsKey(addressId)) {
                // modify existing addresses
                Address modifiedAddress = modifyExistingAddress(updatedAddressSource, addressId, addressesMap);
                updatedAddresses.add(modifiedAddress);
            } else {
                // new address should be added with existing addresses
                // populate updated address
                Address newAddress = populateNewAddressForUpdatedAddress(existingUser, updatedAddressSource);
                updatedAddresses.add(newAddress);
            }
        }

        existingUser.getAddresses().clear();
        existingUser.setAddresses(updatedAddresses);
    }

    private Address modifyExistingAddress(Address updatedAddressSource, Long addressId, Map<Long, Address> addressesMap) {
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
        return modifiedAddress;
    }

    private Address populateNewAddressForUpdatedAddress(CmsUser existingUser, Address updatedAddressSource) {
        if (Division.isNonNull(updatedAddressSource.getDivision())) {
            updatedAddressSource.setDivision(divisionRepository.getOne(updatedAddressSource.getDivision().getDivisionId()));
        }

        if (District.isNonNull(updatedAddressSource.getDistrict())) {
            updatedAddressSource.setDistrict(districtRepository.getOne(updatedAddressSource.getDistrict().getDistrictId()));
        }

        if (Upazila.isNonNull(updatedAddressSource.getUpazila())) {
            updatedAddressSource.setUpazila(upazilaRepository.getOne(updatedAddressSource.getUpazila().getUpazilaId()));
        }
        updatedAddressSource.setCmsUser(existingUser);
        return updatedAddressSource;
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
                AcademicInfo modifiedExistingAcademicInfo = modifyExistingAcademicInfo( updatedAcademicInfo, existingAcademicInfo);
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
        return  existingAcademicInfo;
    }

    public PaginatedCmsUserResponse getAllUsersWithFilter(CmsUserFilter filter, Pageable pageable) {
        Page<CmsUser> cmsUsers = userRepository.search(filter.getCmsUserId(),filter.getUserName(),filter.getRoles(),
                filter.getMobileNumber(),filter.getEmail(),filter.getName(),filter.getGender(),filter.getUserStatus(),filter.getIsActive(), pageable);
        return PaginatedCmsUserResponse.builder()
                .numberOfItems(cmsUsers.getTotalElements()).numberOfPages(cmsUsers.getTotalPages())
                .cmsUserList(cmsUsers.getContent())
                .build();
    }

    public CmsUser getLoggedInUser(Principal principal) {
        Optional<CmsUser> user = userRepository.findByUserName(principal.getName());
        return user.orElse(null);
    }
    public boolean userValidity(Principal principal,Long userId) {
        CmsUser loggedInUser = getLoggedInUser(principal);
        if(loggedInUser.getCmsUserId().equals(userId))
            return true;
        else
            return false;
    }
}

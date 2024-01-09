package com.cms.example.cms.feature.user;

import com.cms.example.cms.entities.Address;
import com.cms.example.cms.entities.CmsUser;
import com.cms.example.cms.entities.District;
import com.cms.example.cms.entities.Division;
import com.cms.example.cms.entities.Upazila;
import com.cms.example.cms.entities.UserRating;
import com.cms.example.cms.enums.EntityFetchType;
import com.cms.example.cms.feature.geo.DistrictRepository;
import com.cms.example.cms.feature.geo.DivisionRepository;
import com.cms.example.cms.feature.geo.UpazilaRepository;
import com.cms.example.cms.feature.userRating.UserRatingRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserRatingRepository userRatingRepository;
    private final DivisionRepository divisionRepository;
    private final DistrictRepository districtRepository;
    private final UpazilaRepository upazilaRepository;

    @Transactional
    public CmsUser saveCmsUser(CmsUser cmsUser) {
        if (UserRating.isNonNull(cmsUser.getUserRating())) {
            cmsUser.setUserRating(userRatingRepository.getOne(cmsUser.getUserRating().getUserRatingId()));
        }
        populateAddress(cmsUser);
        userRepository.save(cmsUser);
        return getCmsUserById(cmsUser.getCmsUserId());
    }

    public CmsUser getCmsUserById(Long cmsUserId) {
        Optional<CmsUser> optionalCmsUser = null;
        optionalCmsUser = userRepository.findById(cmsUserId);

        if (optionalCmsUser.isPresent()) {
            return optionalCmsUser.get();
        } else return null;
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
}

package com.cms.example.cms.feature.user;

import com.cms.example.cms.entities.CmsUser;
import com.cms.example.cms.entities.District;
import com.cms.example.cms.entities.Division;
import com.cms.example.cms.entities.Upazila;
import com.cms.example.cms.entities.UserRating;
import com.cms.example.cms.feature.geo.DistrictRepository;
import com.cms.example.cms.feature.geo.DivisionRepository;
import com.cms.example.cms.feature.geo.UpazilaRepository;
import com.cms.example.cms.feature.userRating.UserRatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if (cmsUser.getUserRating() != null && cmsUser.getUserRating().getUserRatingId() != null) {
            Optional<UserRating> rating = userRatingRepository.findById(cmsUser.getUserRating().getUserRatingId());
        }

        if (cmsUser.getAddresses() != null) {
            cmsUser.getAddresses().forEach(address -> {
                if (address.getDivision() != null && address.getDivision().getDivisionId() != null) {
                    Optional<Division> divisionOptional = divisionRepository.findByIdWithDetails(address.getDivision().getDivisionId());
                    divisionOptional.ifPresent(address::setDivision);
                }

                if (address.getDistrict() != null && address.getDistrict().getDistrictId() != null) {
                    Optional<District> districtOptional = districtRepository.findByIdWithDetails(address.getDistrict().getDistrictId());
                    districtOptional.ifPresent(address::setDistrict);
                }

                if (address.getUpazila() != null && address.getUpazila().getUpazilaId() != null) {
                    Optional<Upazila> upazilaOptional = upazilaRepository.findByIdWithDetails(address.getUpazila().getUpazilaId());
                    upazilaOptional.ifPresent(address::setUpazila);
                }

                address.setCmsUser(cmsUser);
            });
        }

        return userRepository.save(cmsUser);
    }
}

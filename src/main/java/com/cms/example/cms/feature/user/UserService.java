package com.cms.example.cms.feature.user;

import com.cms.example.cms.entities.Address;
import com.cms.example.cms.entities.CmsUser;
import com.cms.example.cms.entities.District;
import com.cms.example.cms.entities.Division;
import com.cms.example.cms.entities.Upazila;
import com.cms.example.cms.entities.UserRating;
import com.cms.example.cms.enums.EntityFetchType;
import com.cms.example.cms.feature.geo.GeoService;
import com.cms.example.cms.feature.userRating.UserRatingService;
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
    private final GeoService geoService;
    private final UserRatingService ratingService;

    @Transactional
    public CmsUser saveCmsUser(CmsUser cmsUser) {
        if (cmsUser.getUserRating() != null) {
            if (cmsUser.getUserRating().getUserRatingId() != null) {
               UserRating rating = ratingService.getUserRatingById(cmsUser.getUserRating().getUserRatingId()));
            }
        }
        if (cmsUser.getAddresses() != null) {
            cmsUser.getAddresses().forEach(address -> {
                if (address.getDivision().getDivisionId() != null) {
                    address.setDivision(geoService.getDivisionById(address.getDivision().getDivisionId(), EntityFetchType.NO_FETCH));
                }
                if (address.getDistrict().getDistrictId() != null) {
                    address.setDistrict(geoService.getDistrictById(address.getDistrict().getDistrictId(), EntityFetchType.NO_FETCH));
                }
                if (address.getUpazila().getUpazilaId() != null) {
                    address.setUpazila(geoService.getUpazilaById(address.getUpazila().getUpazilaId(), EntityFetchType.NO_FETCH));
                }
                address.setCmsUser(cmsUser);
            });
        }
        return userRepository.save(cmsUser);
    }

}

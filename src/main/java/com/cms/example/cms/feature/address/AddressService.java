package com.cms.example.cms.feature.address;

import com.cms.example.cms.entities.Address;
import com.cms.example.cms.entities.CmsUser;
import com.cms.example.cms.entities.District;
import com.cms.example.cms.entities.Division;
import com.cms.example.cms.entities.Upazila;
import com.cms.example.cms.feature.geo.DistrictRepository;
import com.cms.example.cms.feature.geo.DivisionRepository;
import com.cms.example.cms.feature.geo.UpazilaRepository;
import com.cms.example.cms.feature.subject.SubjectRepository;
import com.cms.example.cms.feature.user.CmsUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AddressService {

    private final CmsUserRepository userRepository;
    private final AddressRepository addressRepository;
    private final DivisionRepository divisionRepository;
    private final DistrictRepository districtRepository;
    private final UpazilaRepository upazilaRepository;

    @Transactional
    public Address saveAddress(Address address) {
        Address populatedAddress = populateAddress(address);
        return addressRepository.save(populatedAddress);
    }

    private Address populateAddress(Address address) {
        if (Division.isNonNull(address.getDivision())) {
            address.setDivision(divisionRepository.getOne(address.getDivision().getDivisionId()));
        }
        if (District.isNonNull(address.getDistrict())) {
            address.setDistrict(districtRepository.getOne(address.getDistrict().getDistrictId()));
        }

        if (Upazila.isNonNull(address.getUpazila())) {
            address.setUpazila(upazilaRepository.getOne(address.getUpazila().getUpazilaId()));
        }

        if (CmsUser.isNonNull(address.getCmsUser())) {
            address.setCmsUser(userRepository.getOne(address.getCmsUser().getCmsUserId()));
        }
        return address;
    }

    public Address updateAddress(Long addressId, Address updatedAddress) {
        return null;
    }

    public Address getAddressById(Long addressId) {
        return null;
    }

    public void deleteAddressById(Long addressId) {
    }
}

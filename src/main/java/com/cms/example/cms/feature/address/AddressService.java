package com.cms.example.cms.feature.address;

import com.cms.example.cms.dto.listDataFilterRequestDto.AddressFilter;
import com.cms.example.cms.dto.paginatedResponseDto.PaginatedAddressResponse;
import com.cms.example.cms.entities.Address;
import com.cms.example.cms.entities.CmsUser;
import com.cms.example.cms.entities.District;
import com.cms.example.cms.entities.Division;
import com.cms.example.cms.entities.Upazila;
import com.cms.example.cms.feature.geo.DistrictRepository;
import com.cms.example.cms.feature.geo.DivisionRepository;
import com.cms.example.cms.feature.geo.UpazilaRepository;
import com.cms.example.cms.feature.user.CmsUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
    public Address saveAddress(Long loggedInUserId, Address address) {
        Address populatedAddress = populateAddress(loggedInUserId, address);
        Address savedAddress = addressRepository.save(populatedAddress);
        return getAddressById(savedAddress.getAddressId());
    }

    private Address populateAddress(Long loggedInUserId, Address address) {
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
            address.setCmsUser(userRepository.getOne(loggedInUserId));
        }
        return  address;
    }

    @Transactional
    public Address updateAddress(Long addressId, Address addressSource) {
        Address existingAddress = addressRepository.getOne(addressId);
        //need to update the existing address
        addressSource = modifyExistingAddress(existingAddress, addressSource);
        return getAddressById(addressSource.getAddressId());
    }

    private Address modifyExistingAddress(Address existingAddress, Address updatedAddressSource) {
        //update address
        existingAddress.setAddressType(updatedAddressSource.getAddressType());
        if (updatedAddressSource.getDivision() != null) {
            existingAddress.setDivision(divisionRepository.getOne(updatedAddressSource.getDivision().getDivisionId()));
        }
        if (updatedAddressSource.getDistrict() != null) {
            existingAddress.setDistrict(districtRepository.getOne(updatedAddressSource.getDistrict().getDistrictId()));
        }
        if (updatedAddressSource.getUpazila() != null) {
            existingAddress.setUpazila(upazilaRepository.getOne(updatedAddressSource.getUpazila().getUpazilaId()));
        }
        existingAddress.setIsActive(true);
        return existingAddress;
    }

    public Address getAddressById(Long addressId) {
        return addressRepository.fetchAddressInfoByAddressId(addressId).orElse(null);
    }

    public PaginatedAddressResponse getAllAddressesWithFilter(AddressFilter filter, Pageable pageable) {
        Page<Address> addresses = addressRepository.search(filter.getCmsUserId(), filter.getDivisionName(), filter.getDistrictName(), filter.getUpazilaName(), filter.getIsActive(), pageable);
        return PaginatedAddressResponse.builder()
                .numberOfItems(addresses.getTotalElements())
                .numberOfPages(addresses.getTotalPages())
                .addressList(addresses.getContent())
                .build();
    }
}

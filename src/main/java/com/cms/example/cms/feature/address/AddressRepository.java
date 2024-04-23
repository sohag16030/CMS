package com.cms.example.cms.feature.address;

import com.cms.example.cms.entities.Address;
import com.cms.example.cms.entities.CmsUser;
import com.cms.example.cms.entities.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("SELECT u FROM Address u " +
            "JOIN FETCH u.division " +
            "JOIN FETCH u.district " +
            "JOIN FETCH u.upazila " +
            "WHERE u.addressId = :addressId")
    Optional<Address> fetchAddressInfoByAddressId(@Param("addressId") Long addressId);

    @Query(value = "SELECT addr FROM Address addr " +
            "JOIN FETCH addr.cmsUser cms " +
            "JOIN FETCH addr.division div " +
            "JOIN FETCH addr.district dis " +
            "JOIN FETCH addr.upazila upa " +
            "WHERE " +
            "(:searchText IS NULL OR " +
            "LOWER(div.name) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(dis.name) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(upa.name) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(addr.addressType) = LOWER(:searchText) OR " +
            "LOWER(cms.name) LIKE LOWER(CONCAT('%', :searchText, '%'))) AND " +
            "(:cmsUserId IS NULL OR cms.cmsUserId = :cmsUserId)",
            countQuery = "SELECT COUNT(addr) FROM Address addr")
    Page<Address> search(@Param("searchText") String searchText, @Param("cmsUserId") Long cmsUserId, Pageable pageable);

}

package com.cms.example.cms.feature.address;

import com.cms.example.cms.entities.Address;
import com.cms.example.cms.entities.CmsUser;
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
            "JOIN FETCH addr.division div " +
            "JOIN FETCH addr.district dis " +
            "JOIN FETCH addr.upazila upa " +
            "WHERE " +
            "(:divisionName IS NULL OR div.name LIKE CONCAT('%', :divisionName, '%')) AND " +
            "(:districtName IS NULL OR dis.name LIKE CONCAT('%', :districtName, '%')) AND " +
            "(:upazilaName IS NULL OR upa.name LIKE CONCAT('%', :upazilaName, '%')) AND " +
            "(:isActive IS NULL OR addr.isActive = :isActive)",
            countQuery = "SELECT COUNT(addr) FROM Address addr")
    Page<Address> search(@Param("divisionName") String divisionName,
                         @Param("districtName") String districtName,
                         @Param("upazilaName") String upazilaName,
                         @Param("isActive") Boolean isActive,
                         Pageable pageable);

}

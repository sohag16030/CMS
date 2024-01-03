package com.cms.example.cms.feature.geo;

import com.cms.example.cms.entities.District;
import com.cms.example.cms.entities.Division;
import com.cms.example.cms.entities.Upazila;
import com.cms.example.cms.enums.EntityFetchType;
import com.cms.example.cms.dto.GeoFilterDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GeoService {

    private final DivisionRepository divisionRepository;
    private final DistrictRepository districtRepository;
    private final UpazilaRepository upazilaRepository;
    private final EntityManager entityManager;

//    public Division getDivisionById(Long divisionId, EntityFetchType fetchType) {
//        Optional<Division> optionalDivision = EntityFetchType.NO_FETCH.equals(fetchType) ?
//                divisionRepository.findById(divisionId) :
//                divisionRepository.findByIdWithDetails(divisionId);
//        if (optionalDivision.isPresent()) return optionalDivision.get();
//        else return null;
//    }
    public Division getDivisionById(Long divisionId, EntityFetchType fetchType) {

        Division divisions = (Division) entityManager.createQuery("""
    select div
    from Division div
    left join fetch div.districts as dis
    left join fetch dis.upazilas
    where div.divisionId = :divisionId
    """, Division.class)
                .setParameter("minId", 1L)
                .setParameter("maxId", 50L);
        return divisions;
    }
    public List<Division> getDivisionsByFilter(GeoFilterDto filter) {
        return  divisionRepository.search(filter.getDivisionId(), filter.getDistrictId(), filter.getUpazilaId(), filter.getName(), filter.getNameLocal(), filter.getActive());
    }

    public District getDistrictById(Long districtId, EntityFetchType fetchType) {
        Optional<District> optionalDistrict = EntityFetchType.NO_FETCH.equals(fetchType) ?
                districtRepository.findById(districtId) :
                districtRepository.findByIdWithDetails(districtId);
        if (optionalDistrict.isPresent()) return optionalDistrict.get();
        else return null;
    }

    public List<District> getDistrictsByFilter(GeoFilterDto filter) {
        return districtRepository.search(filter.getDivisionId(), filter.getDistrictId(), filter.getUpazilaId(), filter.getName(), filter.getNameLocal(), filter.getActive());
    }

    public Upazila getUpazilaById(Long upazilaId, EntityFetchType fetchType) {
        Optional<Upazila> optionalUpazila = EntityFetchType.NO_FETCH.equals(fetchType) ?
                upazilaRepository.findById(upazilaId) :
                upazilaRepository.findByIdWithDetails(upazilaId);
        if (optionalUpazila.isPresent()) return optionalUpazila.get();
        else return null;
    }

    public List<Upazila> getUpazilaByFilter(GeoFilterDto filter) {
        return upazilaRepository.search(filter.getDivisionId(), filter.getDistrictId(), filter.getUpazilaId(), filter.getName(), filter.getNameLocal(), filter.getActive());
    }
}
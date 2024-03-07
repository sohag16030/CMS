package com.cms.example.cms.feature.academicInfo;

import com.cms.example.cms.entities.AcademicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcademicInfoRepository extends JpaRepository<AcademicInfo, Long> {
    @Query("SELECT a FROM AcademicInfo a LEFT JOIN FETCH a.subjects WHERE a.academicInfoId IN :academicInfoIds")
    List<AcademicInfo> fetchSubjectsByAcademicInfoIdIn(@Param("academicInfoIds") List<Long> academicInfoIds);
}


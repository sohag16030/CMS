package com.cms.example.cms.dto.entityDto.requestDto;

import com.cms.example.cms.entities.District;
import com.cms.example.cms.entities.Upazila;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpazilaRequestDto {

    private Long upazilaId;

    private String name;

    private Boolean active;

    private DistrictRequestDto districtRequestDto;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}


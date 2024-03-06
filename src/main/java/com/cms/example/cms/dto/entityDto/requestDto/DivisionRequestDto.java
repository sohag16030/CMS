package com.cms.example.cms.dto.entityDto.requestDto;

import com.cms.example.cms.entities.District;
import com.cms.example.cms.entities.Division;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DivisionRequestDto {

    private Long divisionId;

    private String name;

    private Boolean active;

    @JsonIgnoreProperties(value = {"division"}, allowSetters = true)
    private List<DistrictRequestDto> district;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @JsonIgnore
    public static Boolean isNonNull(DivisionRequestDto division){
        return Objects.nonNull(division) && Objects.nonNull(division.getDivisionId());
    }
}


package com.cms.example.cms.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "UPAZILA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Upazila {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UPAZILA_ID")
    private Long upazilaId;

    @NotNull
    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @NotNull
    @Column(name = "NAME_LOCAL", unique = true, nullable = false)
    private String nameLocal;

    @NotNull
    @Column(name = "ACTIVE", nullable = false)
    private Boolean active;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DISTRICT_ID", nullable = false)
    private District district;

    @OneToMany(mappedBy = "upazila", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Address> addresses;

    @Column(name = "CREATED_AT", nullable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    @UpdateTimestamp
    private Date updatedAt;
}


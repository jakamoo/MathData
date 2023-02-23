package com.digitopia.caseStudy.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Table(name="ORGANIZATION_TABLE")
@Entity
@Data

public class OrganizationEntity extends BaseEntity {
    private String name;
    @Column(unique = true)
    private String registryNumber;
    private String normalizedName;
    private String contactEmail;
    private int foundedYear;
    private String phone;
    private int companySize;
    @ManyToMany(mappedBy = "organizations",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<UserEntity> users;
}

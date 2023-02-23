package com.digitopia.caseStudy.entity;


import com.digitopia.caseStudy.status.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;

import java.util.List;


@Table(name="USER_TABLE")
@Entity
@Data
public class UserEntity extends BaseEntity {



    @Enumerated(EnumType.STRING)
    @Column(name = "user_status")
    private UserStatus status;

    @Pattern(regexp = "^[a-z A-Z]+$")
    @Column(name = "full_name", length = 100)
    private String fullName;

    @Email
    @Column(name = "email", length = 200,unique = true)

    private String email;

    @Column(name="normalized_name",length = 100)
    private String normalizedName;

    @ManyToMany(fetch = FetchType.EAGER )
    @JoinTable(
            name = "USER_ORGANIZATION_TABLE",
            joinColumns = @JoinColumn(name = "USER_ID",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ORGANIZATION_ID",referencedColumnName = "id"))
    private List<OrganizationEntity> organizations;


}

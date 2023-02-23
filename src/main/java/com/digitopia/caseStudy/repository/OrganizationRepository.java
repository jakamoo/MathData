package com.digitopia.caseStudy.repository;

import com.digitopia.caseStudy.entity.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<OrganizationEntity,Long> {
 Optional<OrganizationEntity> findById(Long organizationId);
 Optional<OrganizationEntity> findByRegistryNumber(Long registryNumber);
 Optional<OrganizationEntity> findByNormalizedNameContainingAndFoundedYearAndCompanySize(String name, Integer year, Integer companySize);

}

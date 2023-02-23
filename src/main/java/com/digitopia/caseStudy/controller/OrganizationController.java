package com.digitopia.caseStudy.controller;

import com.digitopia.caseStudy.dto.OrganizationDto;
import com.digitopia.caseStudy.dto.UserDto;
import com.digitopia.caseStudy.entity.OrganizationEntity;
import com.digitopia.caseStudy.repository.OrganizationRepository;
import com.digitopia.caseStudy.repository.UserRepository;
import com.digitopia.caseStudy.service.OrganizationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/organization")

public class OrganizationController {

    private final OrganizationService organizationService;

    @PostMapping("/create/{creatorId}")
        public ResponseEntity<OrganizationDto> createOrganization(@RequestBody OrganizationDto organizationDto, @PathVariable Long creatorId) {

            OrganizationDto returnOrganizationDto = organizationService.createOrganization(organizationDto,creatorId);
            return ResponseEntity.ok(returnOrganizationDto);

        }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<Void> deleteOrganization(@PathVariable Long id) {
            if (!organizationService.deleteOrganization(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        @GetMapping("/getAll")
        public ResponseEntity<List<OrganizationDto>> getAll() {
            List<OrganizationDto> organizations = organizationService.getOrganizations();

            return ResponseEntity.ok(organizations);
        }

        @PutMapping("/update/{organizationId}")
        public ResponseEntity<Void> updateOrganization(@PathVariable Long organizationId,
                                                       @RequestBody OrganizationEntity organizationEntity,
                                                       @RequestParam Long updatedId) {

            OrganizationEntity organization = organizationService.updateOrganization(organizationId, organizationEntity,updatedId);
            if(organization != null)
                return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }

        @GetMapping("/getUsersByRegistryNumber")
        public ResponseEntity<List<UserDto>>getUsersByRegistryId(@RequestParam Long registryNumber){

            List<UserDto> users = organizationService.getUsersForOrganization(registryNumber);
            if(users != null)
                return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @GetMapping("/getOrganizations")
        public ResponseEntity<List<UserDto>>getOrganizationsByFilter(@RequestParam String normalizedName,
                                                                     @RequestParam int year,
                                                                     @RequestParam int companySize){

            List<OrganizationDto> organizations = organizationService.getOrganizations();
            if(organizations != null)
                return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }




/*
        @GetMapping("/{registryNumber}")
        public ResponseEntity<OrganizationEntity> getOrganization(@PathVariable String registryNumber) {
            return organizationService.findByRegistryNumber(registryNumber)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }

        @GetMapping("/search")
        public ResponseEntity<List<Organization>> searchOrganizations(
                @RequestParam("normalizedName") Optional<String> normalizedName,
                @RequestParam("yearFounded") Optional<Integer> yearFounded,
                @RequestParam("companySize") Optional<Integer> companySize) {
            return ResponseEntity.ok(
                    organizationService.searchOrganizations(normalizedName, yearFounded, companySize));
        }

        @GetMapping("/{registryNumber}/users")
        public ResponseEntity<List<UserEntity>> getUsers(@PathVariable String registryNumber) {
            return organizationService.findByRegistryNumber(registryNumber)
                    .map(OrganizationEntity::getUsers)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }
        */

    }



package com.cg.nordea.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cg.nordea.entities.CertificationDetailsOfResource;

@Repository
public interface CertificationDetailsOfResourceRepository extends CrudRepository<CertificationDetailsOfResource, Long> {

}

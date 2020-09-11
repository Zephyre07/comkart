package com.cg.nordea.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cg.nordea.entities.CertificationDetailsOfResource;

@Repository
public interface CertificationDetailsOfResourceRepository extends CrudRepository<CertificationDetailsOfResource, Long> {

	Optional<List<CertificationDetailsOfResource>> findByEmployeeID(String employeeID);
	
	@Query("SELECT DISTINCT employeeID FROM CertificationDetailsOfResource")
	Optional<List<String>> findCertEmpId();
}

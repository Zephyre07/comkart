package com.cg.nordea.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cg.nordea.entities.CertCatergoryDetails;

@Repository
public interface CertCatergoryDetailsRepository extends CrudRepository<CertCatergoryDetails, Long> {

}

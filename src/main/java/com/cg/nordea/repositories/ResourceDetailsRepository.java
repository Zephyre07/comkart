package com.cg.nordea.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cg.nordea.entities.ResourceDetails;

@Repository
public interface ResourceDetailsRepository extends CrudRepository<ResourceDetails, String> {

}

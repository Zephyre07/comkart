package com.cg.nordea.service;

import java.util.List;

import com.cg.nordea.dto.CertCategoryDetailsDto;
import com.cg.nordea.dto.CertResourceDetails;
import com.cg.nordea.entities.Currency;
import com.cg.nordea.entities.ResourceDetails;
import com.cg.nordea.exceptions.NoDataFoundException;

public interface ComKartService {

	public List<Currency> getCurrency();
	
	public ResourceDetails getResource(String employeeID) throws NoDataFoundException;
	
	public CertResourceDetails getCertificates(String employeeID) throws NoDataFoundException;

	public List<CertCategoryDetailsDto> getCertCategoryDetails() throws NoDataFoundException;
}

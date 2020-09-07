package com.cg.nordea.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.nordea.entities.Currency;
import com.cg.nordea.entities.ResourceDetails;
import com.cg.nordea.exceptions.NoDataFoundException;
import com.cg.nordea.repositories.CurrencyRepository;
import com.cg.nordea.repositories.ResourceDetailsRepository;
import com.cg.nordea.service.ComKartService;


@Service
public class ComKartServiceImpl  implements ComKartService {
	
	@Autowired
	CurrencyRepository CurrencyRepository;
	
	@Autowired
	ResourceDetailsRepository resourceDetailsRepository;

	public List<Currency> getCurrency() {
		return (List<Currency>) CurrencyRepository.findAll();
	}

	@Override
	public ResourceDetails getResource(String employeeID) throws NoDataFoundException {
		
		return resourceDetailsRepository.findById(employeeID)
				.orElseThrow(() -> new NoDataFoundException("User not present with " + employeeID));
		
		
	}
}

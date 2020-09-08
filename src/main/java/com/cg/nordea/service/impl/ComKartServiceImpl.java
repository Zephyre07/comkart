package com.cg.nordea.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.nordea.dto.CertCategoryDetailsDto;
import com.cg.nordea.dto.CertResourceDetails;
import com.cg.nordea.dto.CertificationDetailsDto;
import com.cg.nordea.entities.CertCatergoryDetails;
import com.cg.nordea.entities.CertificationDetailsOfResource;
import com.cg.nordea.entities.Currency;
import com.cg.nordea.entities.ResourceDetails;
import com.cg.nordea.exceptions.NoDataFoundException;
import com.cg.nordea.repositories.CertCatergoryDetailsRepository;
import com.cg.nordea.repositories.CertificationDetailsOfResourceRepository;
import com.cg.nordea.repositories.CurrencyRepository;
import com.cg.nordea.repositories.ResourceDetailsRepository;
import com.cg.nordea.service.ComKartService;

@Service
public class ComKartServiceImpl implements ComKartService {

	@Autowired
	CurrencyRepository CurrencyRepository;

	@Autowired
	ResourceDetailsRepository resourceDetailsRepository;

	@Autowired
	CertCatergoryDetailsRepository certCatergoryDetailsRepository;

	@Autowired
	CertificationDetailsOfResourceRepository certificationDetailsOfResourceRepository;

	@Autowired
	private ModelMapper modelMapper;

	public List<Currency> getCurrency() {
		return (List<Currency>) CurrencyRepository.findAll();
	}

	@Override
	public ResourceDetails getResource(String employeeID) throws NoDataFoundException {

		return resourceDetailsRepository.findById(employeeID)
				.orElseThrow(() -> new NoDataFoundException("User not present with " + employeeID));

	}

	@Override
	public CertResourceDetails getCertificates(String employeeID) throws NoDataFoundException {
		ResourceDetails resourceDetails = resourceDetailsRepository.findById(employeeID)
				.orElseThrow(() -> new NoDataFoundException("User not present with " + employeeID));

		CertResourceDetails certResourceDetails = modelMapper.map(resourceDetails, CertResourceDetails.class);

		List<CertificationDetailsOfResource> certList = certificationDetailsOfResourceRepository
				.findByEmployeeID(employeeID)
				.orElseThrow(() -> new NoDataFoundException("Certificate details not available for " + employeeID));
		
		
		List<CertificationDetailsDto> certDetailDtoList = certList.stream().map(certDetails -> {
			try {
				return loadCertificationData(certDetails);
			} catch (NoDataFoundException e) {
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());
		
		certResourceDetails.setCertificationList(certDetailDtoList);
		certResourceDetails.setResponseCode("200");
		certResourceDetails.setResponseMessage("Success");
		return certResourceDetails;
	}

	public CertificationDetailsDto loadCertificationData(CertificationDetailsOfResource certDetail) throws NoDataFoundException {
		CertificationDetailsDto certDetailsDto = new CertificationDetailsDto();
		CertCatergoryDetails categoryDetails = certCatergoryDetailsRepository.findById(certDetail.getCertCategoryId())
				.orElseThrow(() -> new NoDataFoundException("Certificate details not available for " + certDetail.getEmployeeID()));
		
		certDetailsDto.setProvider(categoryDetails.getCertProvider());
		certDetailsDto.setTechName(categoryDetails.getTechName());
		certDetailsDto.setCertificateName(categoryDetails.getCertName());
		certDetailsDto.setCertificationDate(certDetail.getCertificationDate());
		certDetailsDto.setValidFrom(certDetail.getValidFromDate());
		certDetailsDto.setValidTo(certDetail.getValidToDate());
		
		return certDetailsDto;
		

	}
	
	@Override
	public List<CertCategoryDetailsDto> getCertCategoryDetails() throws NoDataFoundException {
		
		List<CertCatergoryDetails> categoryDetails = (List<CertCatergoryDetails>) certCatergoryDetailsRepository.findAll();
		
		List<CertCategoryDetailsDto> certDetailDtoList = categoryDetails.stream().map(categoryDetail -> {
			try {
				return loadCertCategoryData(categoryDetail);
			} catch (NoDataFoundException e) {
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());
		
		return certDetailDtoList;
	}
	
	public CertCategoryDetailsDto loadCertCategoryData(CertCatergoryDetails categoryDetails) throws NoDataFoundException {
		CertCategoryDetailsDto certDetailsDto = new CertCategoryDetailsDto();
				
		certDetailsDto.setCertCategoryId(categoryDetails.getCertCategoryId());
		certDetailsDto.setProvider(categoryDetails.getCertProvider());
		certDetailsDto.setTechName(categoryDetails.getTechName());
		certDetailsDto.setCertificateName(categoryDetails.getCertName());
		
		return certDetailsDto;
		

	}
	
}

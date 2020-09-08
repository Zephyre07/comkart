package com.cg.nordea.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.nordea.dto.CertCategoryDetailsDto;
import com.cg.nordea.dto.CertResourceDetails;
import com.cg.nordea.dto.CertResourceMappingDto;
import com.cg.nordea.dto.ResponseDTO;
import com.cg.nordea.entities.Currency;
import com.cg.nordea.entities.ResourceDetails;
import com.cg.nordea.exceptions.NoDataFoundException;
import com.cg.nordea.service.impl.ComKartServiceImpl;

@RestController
@RequestMapping("/comKart")
public class ComKartController {

	@Autowired
	ComKartServiceImpl comKartServiceImpl;

	@GetMapping(value = "/currencies")
	public ResponseEntity<List<Currency>> getAllAccounts() {
		List<Currency> curr = comKartServiceImpl.getCurrency();
		return new ResponseEntity<>(curr, HttpStatus.OK);
	}

	@GetMapping(value = "/resource/{empId}")
	public ResponseEntity<ResourceDetails> getResourceDetails(@PathVariable String empId) throws NoDataFoundException {
		return new ResponseEntity<>(comKartServiceImpl.getResource(empId), HttpStatus.OK);
	}

	@GetMapping(value = "/certificate/{empId}")
	public ResponseEntity<CertResourceDetails> getCertificateDetails(@PathVariable String empId)
			throws NoDataFoundException {
		CertResourceDetails details = comKartServiceImpl.getCertificates(empId);
		return new ResponseEntity<>(details, HttpStatus.OK);
	}
	
	@GetMapping(value = "/certification")
	public ResponseEntity<List<CertCategoryDetailsDto>> getCertCategoryDetails() 
			throws NoDataFoundException {
		List<CertCategoryDetailsDto> certCategoryDetails = comKartServiceImpl.getCertCategoryDetails();
		return new ResponseEntity<>(certCategoryDetails, HttpStatus.OK);
	}
	
	@PostMapping("/certificate/save")
	public ResponseEntity<ResponseDTO> postJson(@RequestBody CertResourceMappingDto certResourceMappingDto) {
		
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("Certificate Successfuly saved");
		
	    try {
	    	certResourceMappingDto.setCreatedBY("88063_FS");//TO-DO -> will be login user id
	    	comKartServiceImpl.saveCertResourceMapping(certResourceMappingDto);
	    } catch(Exception e) {
	    	responseDTO.setMessage("Error occurred while saving Certificate");
	    }
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		
	}

}

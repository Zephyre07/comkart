package com.cg.nordea.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CertificationDetailsDto {
	
	String techName;
	
	String provider;
	
	String certificateName;
	
	LocalDate certificationDate;
	
	LocalDate validFrom;
	
	LocalDate validTo;
}

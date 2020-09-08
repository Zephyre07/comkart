package com.cg.nordea.dto;

import java.util.List;

import lombok.Data;

@Data
public class CertResourceDetails {
	
	private String employeeId;

	private String employeeName;

	private String resourceUserId;

	private String empStatus;
	
	private String mobileNumber;
	
	private String capgeminiEmailId;
	
	private String nordeaEmailId;
	
	private String ggId;
	
	List<CertificationDetailsDto> certificationList;
	
	String responseMessage;
	
	String responseCode;

}

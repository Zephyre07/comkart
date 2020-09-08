package com.cg.nordea.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CertResourceMappingDto {
	
	private static final String dateFormat = "dd/MM/yyyy";

	Long certCategoryId;

	private String employeeID;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateFormat)
	private LocalDate certificationDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateFormat)
	private LocalDate validFromDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateFormat)
	private LocalDate validToDate;
	
	private String createdBY;

}

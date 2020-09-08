package com.cg.nordea.entities;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "CERT_RESOURCE_MAPPING_DETAILS")
public class CertificationDetailsOfResource {
	@Id
	@Column(name = "RESOURCE_CERTIFICATE_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long resourceCertId;

	@Column(name = "CERTIFICATE_ID")
	Long certCategoryId;
	
	@Column(name = "Emp_ID")
	private String employeeID;

	@Column(name = "CERTIFICATION_DATE")
	private LocalDate certificationDate;

	@Column(name = "VALID_FROM_DATE")
	private LocalDate validFromDate;

	@Column(name = "VALID_TO_DATE")
	private LocalDate validToDate;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "Created_By")
	private String createdBY;
	
	@Column(name = "Updated_By")
	private String updatedBY;

	@Column(name = "Created_Date")
	private Date createdDate;

	@Column(name = "Updated_Date")
	private Date updatedDate;

}

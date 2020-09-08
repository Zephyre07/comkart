package com.cg.nordea.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "ncoms_resource_details")
public class ResourceDetails {
	@Id
	@Column(name = "Emp_ID")
	private String employeeId;

	@Column(name = "Emp_Name")
	private String employeeName;

	@Column(name = "Resource_User_Id")
	private String resourceUserId;

	@Column(name = "Emp_Status")
	private String empStatus;

	@Column(name = "Mobile_Number")
	private String mobileNumber;

	@Column(name = "Capgemini_E_Mail_Id")
	private String capgeminiEmailId;

	@Column(name = "Nordea_E_Mail_Id")
	private String nordeaEmailId;

	@Column(name = "GGID")
	private String ggId;

	@Column(name = "CreatedBy")
	private String createdBY;

	@Column(name = "UpdatedBy")
	private String updatedBY;

	@Column(name = "CreatedDate", columnDefinition = "TIMESTAMP")
	private LocalDateTime createdDate;

	@Column(name = "UpdatedDate", columnDefinition = "TIMESTAMP")
	private LocalDateTime updatedDate;

}

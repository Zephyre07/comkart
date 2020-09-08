package com.cg.nordea.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "cert_category_details")
public class CertCatergoryDetails {
	@Id
	@Column(name = "CERTIFICATE_ID")
	Long certCategoryId;

	@Column(name = "TECH_NAME")
	private String techName;

	@Column(name = "CERT_PROVIDER")
	private String certProvider;

	@Column(name = "CERT_NAME")
	private String certName;

	@Column(name = "Created_By")
	private String createdBY;

	@Column(name = "Updated_By")
	private String updatedBY;

	@Column(name = "Created_Date", columnDefinition = "TIMESTAMP")
	private LocalDateTime createdDate;

	@Column(name = "Updated_Date", columnDefinition = "TIMESTAMP")
	private LocalDateTime updatedDate;

}

package com.cg.nordea.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "currency")
public class Currency {
	@Id
	@Column(name = "ID")
	Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "CREATEDBY")
	private String createdBY;

	@Column(name = "UPDATEDBY")
	private String updatedBY;

	@Column(name = "CREATEDDATE", columnDefinition = "TIMESTAMP")
	private LocalDateTime createdDate;

	@Column(name = "UPDATEDDATE", columnDefinition = "TIMESTAMP")
	private LocalDateTime updatedDate;

}

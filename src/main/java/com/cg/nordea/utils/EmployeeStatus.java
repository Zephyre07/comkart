package com.cg.nordea.utils;

public enum EmployeeStatus {
	
	RELEASED("R"),
	ACTIVE("A"),
	INACTIVE("I"),
	EMPLOYED("E");
	
	String status;
	private EmployeeStatus(String status) {
		this.status = status;
	}
}
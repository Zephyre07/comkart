package com.cg.nordea.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ResponseDTO implements Serializable {
	
	Integer code;

	String message;

	String description;
}
package com.cg.nordea.exceptions;

import lombok.Data;

/**
 * This class is used to handle error messages.
 * 
 * @author Capgemini
 * @since 09/03/2020
 */
@Data
public class MessagesErrorVO {

	private String code;
	private String description;
	private String element;

	public MessagesErrorVO() {

	}

	public MessagesErrorVO(String code, String description, String element) {

		this.code = code;
		this.description = description;
		this.element = element;
	}
}

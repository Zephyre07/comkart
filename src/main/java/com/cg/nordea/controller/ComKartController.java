package com.cg.nordea.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.nordea.entities.Currency;
import com.cg.nordea.service.impl.ComKartServiceImpl;


@RestController
@RequestMapping("/comKart")
public class ComKartController {
	
	@Autowired
	ComKartServiceImpl comKartServiceImpl;
	
	@GetMapping(value = "/currencies")
	public ResponseEntity<List<Currency>> getAllAccounts() {
		List<Currency> curr = comKartServiceImpl.getCurrency();
		return new ResponseEntity<>(curr, HttpStatus.OK);
	}

}

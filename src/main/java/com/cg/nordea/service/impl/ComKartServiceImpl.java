package com.cg.nordea.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.nordea.entities.Currency;
import com.cg.nordea.repositories.CurrencyRepository;
import com.cg.nordea.service.ComKartService;


@Service
public class ComKartServiceImpl  implements ComKartService {
	
	@Autowired
	CurrencyRepository CurrencyRepository;

	public List<Currency> getCurrency() {
		return (List<Currency>) CurrencyRepository.findAll();
	}
}

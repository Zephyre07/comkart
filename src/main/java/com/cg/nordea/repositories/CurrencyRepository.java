package com.cg.nordea.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cg.nordea.entities.Currency;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, Long> {

}

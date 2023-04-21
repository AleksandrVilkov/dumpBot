package com.dumpBot.storage.repository;

import com.dumpBot.storage.entity.CurrencyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends CrudRepository<CurrencyEntity, Integer> {
}

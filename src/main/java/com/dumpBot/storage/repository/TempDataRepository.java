package com.dumpBot.storage.repository;

import com.dumpBot.storage.entity.TempData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TempDataRepository extends CrudRepository<TempData, Integer> {
}

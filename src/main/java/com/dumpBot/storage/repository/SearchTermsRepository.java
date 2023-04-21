package com.dumpBot.storage.repository;

import com.dumpBot.storage.entity.SearchTermsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchTermsRepository extends CrudRepository<SearchTermsEntity, Integer> {
}

package com.dumpBot.storage.repository;

import com.dumpBot.storage.entity.SearchTerms;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchTermsRepository extends CrudRepository<SearchTerms, Integer> {
}

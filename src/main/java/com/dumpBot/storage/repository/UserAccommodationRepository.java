package com.dumpBot.storage.repository;

import com.dumpBot.storage.entity.UserAccommodationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccommodationRepository extends CrudRepository<UserAccommodationEntity, Integer> {
}

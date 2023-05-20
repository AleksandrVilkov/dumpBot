package com.dumpBot.storage.repository;

import com.dumpBot.storage.entity.UserAccommodationEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAccommodationRepository extends CrudRepository<UserAccommodationEntity, Integer> {
    @Query(value = "select * from user_accommodation", nativeQuery = true)
    List<Object[]> getAll();
}

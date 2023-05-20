package com.dumpBot.storage.repository;

import com.dumpBot.storage.entity.PhotoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PhotoRepository extends CrudRepository<PhotoEntity, Integer> {
    @Query(value = "select * from user_accommodation_photo c WHERE c.user_accommodation_id = ?1", nativeQuery = true)
    List<Object[]> findAllBy(int userAccommodationId);
}

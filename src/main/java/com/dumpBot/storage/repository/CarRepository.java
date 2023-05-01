package com.dumpBot.storage.repository;

import com.dumpBot.storage.entity.CarEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends CrudRepository<CarEntity, Integer> {
    @Query(value = "select * from car c", nativeQuery = true)
    public List<Object[]> getAllCars();
}


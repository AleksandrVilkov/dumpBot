package com.dumpBot.storage.repository;

import com.dumpBot.storage.entity.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends CrudRepository<Car, Integer> {
    @Query(value = "select distinct c.concern from car c", nativeQuery = true)
    public List<Object[]> getUniqueCarConcern();
    @Query(value = "select distinct c.brand from car c WHERE c.concern = ?1", nativeQuery = true)
    public List<Object[]> getUniqueCarBrandByConcern(String concern);
    @Query(value = "select distinct c.model from car c WHERE c.brand = ?1", nativeQuery = true)
    public List<Object[]> getUniqueCarModelsByBrand(String brand);
    @Query(value = "select distinct c.engine from car c WHERE c.brand = ?1 and c.model = ?2", nativeQuery = true)
    public List<Object[]> getUniqueCarEngine(String brand, String model);
    @Query(value = "select distinct c.boltPattern from car c WHERE c.brand = ?1 and c.model = ?2", nativeQuery = true)
    public List<Object[]> getUniqueCarBoltPattern(String brand, String model);
    @Query(value = "select * from car c WHERE c.brand = ?1 and c.model = ?2 and c.engine = ?3 and c.boltPattern = ?4", nativeQuery = true)
    public List<Object[]> getCar(String brand, String model, String engine, String boltPattern);
}


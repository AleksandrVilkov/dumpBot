package com.dumpBot.storage.repository;

import com.dumpBot.storage.entity.CarEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends CrudRepository<CarEntity, Integer> {
    @Query(value = "select distinct c.concern from car c", nativeQuery = true)
    public List<Object[]> getUniqueCarConcern();
    @Query(value = "select distinct c.concern from car c WHERE c.concern LIKE %:pattern%", nativeQuery = true)
    public List<Object[]> getConcernByPattern(String pattern);

    @Query(value = "select distinct c.brand from car c WHERE c.concern LIKE %:concern% and c.brand LIKE %:pattern%", nativeQuery = true)
    public List<Object[]> getBrandByPattern(String concern, String pattern);

    @Query(value = "select distinct c.model from car c WHERE c.concern LIKE %:concern% and c.brand LIKE %:brand% and c.model LIKE %:pattern%", nativeQuery = true)
    public List<Object[]> getModelByPattern(String concern, String brand, String pattern);
    @Query(value = "select * from car c WHERE c.concern = ?1 and c.brand = ?2 and c.model = ?3", nativeQuery = true)
    public List<Object[]> getCars(String concern, String brand, String model);

    @Query(value = "select distinct c.bolt_Pattern from car c WHERE c.brand = ?1 and c.model = ?2", nativeQuery = true)
    public List<Object[]> getUniqueCarBoltPattern(String brand, String model);
    @Query(value = "select * from car c WHERE c.brand = ?1 and c.model = ?2 and c.engine = ?3 and c.bolt_Pattern = ?4", nativeQuery = true)
    public List<Object[]> getCar(String brand, String model, String engine, String boltPattern);
}


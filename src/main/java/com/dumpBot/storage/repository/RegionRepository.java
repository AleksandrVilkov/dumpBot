package com.dumpBot.storage.repository;

import com.dumpBot.storage.entity.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionRepository extends JpaRepository<RegionEntity, Integer> {
    @Query(value = "select * from region", nativeQuery = true)
    public List<Object[]> getAllCities();

}

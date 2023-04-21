package com.dumpBot.storage.repository;


import com.dumpBot.storage.entity.TempDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TempDataRepository extends JpaRepository<TempDataEntity, Integer> {
    List<TempDataEntity> findByToken(String token);
}

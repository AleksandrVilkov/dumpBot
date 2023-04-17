package com.dumpBot.storage.repository;


import com.dumpBot.storage.entity.TempData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TempDataRepository extends JpaRepository<TempData, Integer> {
    List<TempData> findByToken(String token);
}

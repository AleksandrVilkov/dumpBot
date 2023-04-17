package com.dumpBot.storage.repository;


import com.dumpBot.storage.entity.TempData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TempDataRepository extends JpaRepository<TempData, Integer> {
}

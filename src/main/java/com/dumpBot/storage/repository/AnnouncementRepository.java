package com.dumpBot.storage.repository;

import com.dumpBot.storage.entity.AnnouncementEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends CrudRepository<AnnouncementEntity, Integer> {
}

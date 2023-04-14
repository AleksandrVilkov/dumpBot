package com.dumpBot.storage.repository;

import com.dumpBot.storage.entity.Announcement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends CrudRepository<Announcement, Integer> {
}

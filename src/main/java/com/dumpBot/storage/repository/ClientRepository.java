package com.dumpBot.storage.repository;

import com.dumpBot.storage.entity.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {

}

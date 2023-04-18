package com.dumpBot.storage.repository;

import com.dumpBot.storage.entity.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {
    List<Client> findByLogin(String login);

}

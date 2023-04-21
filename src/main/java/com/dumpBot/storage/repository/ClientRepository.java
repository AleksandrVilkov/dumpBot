package com.dumpBot.storage.repository;

import com.dumpBot.storage.entity.ClientEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClientRepository extends CrudRepository<ClientEntity, Integer> {
    List<ClientEntity> findByLogin(String login);

}

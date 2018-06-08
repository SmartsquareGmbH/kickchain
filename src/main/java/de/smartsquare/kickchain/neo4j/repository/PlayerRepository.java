package de.smartsquare.kickchain.neo4j.repository;

import de.smartsquare.kickchain.neo4j.entities.PlayerNodeEntity;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<PlayerNodeEntity, String> {

    PlayerNodeEntity findByName(String name);

    String deleteByName(String name);

}

package de.smartsquare.kickchain.neo4j.repository;

import de.smartsquare.kickchain.neo4j.entities.GameNodeEntity;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<GameNodeEntity, Long> {

}

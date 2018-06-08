package de.smartsquare.kickchain.neo4j.repository;

import de.smartsquare.kickchain.neo4j.entities.HasGamesRelationshipEntity;
import org.springframework.data.repository.CrudRepository;

public interface HasGameRepository extends CrudRepository<HasGamesRelationshipEntity, Long> {

}

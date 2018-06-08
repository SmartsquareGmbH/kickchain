package de.smartsquare.kickchain.neo4j.repository;

import de.smartsquare.kickchain.neo4j.entities.FollowsRelationshipEntity;
import org.springframework.data.repository.CrudRepository;

public interface FollowsRepository extends CrudRepository<FollowsRelationshipEntity, Long> {

}

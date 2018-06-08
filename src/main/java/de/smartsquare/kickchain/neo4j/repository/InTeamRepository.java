package de.smartsquare.kickchain.neo4j.repository;

import de.smartsquare.kickchain.neo4j.entities.InTeamRelationshipEntity;
import org.springframework.data.repository.CrudRepository;

public interface InTeamRepository extends CrudRepository<InTeamRelationshipEntity, Long> {

}

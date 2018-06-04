package de.smartsquare.kickchain.neo4j.repository;

import de.smartsquare.kickchain.neo4j.entities.BlockNodeEntity;
import org.springframework.data.repository.CrudRepository;

public interface BlockRepository extends CrudRepository<BlockNodeEntity, Long> {


}

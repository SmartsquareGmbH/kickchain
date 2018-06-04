package de.smartsquare.kickchain.neo4j.repository;

import de.smartsquare.kickchain.neo4j.entities.BlockchainNodeEntity;
import org.springframework.data.repository.CrudRepository;

public interface BlockchainRepository extends CrudRepository<BlockchainNodeEntity, String> {

    BlockchainNodeEntity findByName(String name);

}

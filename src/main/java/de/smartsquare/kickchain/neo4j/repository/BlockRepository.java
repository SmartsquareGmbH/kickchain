package de.smartsquare.kickchain.neo4j.repository;

import de.smartsquare.kickchain.neo4j.entities.BlockNodeEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlockRepository extends CrudRepository<BlockNodeEntity, Long> {

    List<BlockNodeEntity> findByBlockchain(String blockchain);

    @Query("match (b:BlockNodeEntity) return b order by (b.proof) asc limit 1")
    BlockNodeEntity findStartByBlockchain(@Param("blockchain") String blockchain);

    @Query("match (b:BlockNodeEntity) return b order by (b.proof) desc limit 1")
    BlockNodeEntity findEndByBlockchain(@Param("blockchain") String blockchain);

}

package de.smartsquare.kickchain.jpa.repository;

import de.smartsquare.kickchain.jpa.entities.BlockEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaBlockRepository extends CrudRepository<BlockEntity,Long> {

    List<BlockEntity> findAllByBlockchain(String blockchain);

    void deleteAllByBlockchain(String blockchain);

}

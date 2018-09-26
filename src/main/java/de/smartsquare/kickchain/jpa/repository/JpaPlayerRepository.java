package de.smartsquare.kickchain.jpa.repository;

import de.smartsquare.kickchain.jpa.entities.PlayerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPlayerRepository extends CrudRepository<PlayerEntity, String> {

    void deleteByName(String name);

}

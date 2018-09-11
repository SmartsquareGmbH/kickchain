package de.smartsquare.kickchain.jpa.repository;

import de.smartsquare.kickchain.jpa.entities.GameEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaGameRepository extends CrudRepository<GameEntity,Long> {
}

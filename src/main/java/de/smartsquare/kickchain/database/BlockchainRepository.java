package de.smartsquare.kickchain.database;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockchainRepository extends JpaRepository<BlockchainEntity, String> {
}

package de.smartsquare.kickchain;

import de.smartsquare.kickchain.neo4j.entities.BlockNodeEntity;
import de.smartsquare.kickchain.neo4j.repository.BlockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import java.time.Instant;

@SpringBootApplication
@EnableNeo4jRepositories(basePackages = "de.smartsquare.kickchain.neo4j.repository")
//@SpringBootApplication(exclude={Neo4jDataAutoConfiguration.class})

//@EnableNeo4jRepositories(basePackages = "de.smartsquare.kickchain.neo4j.repository")
public class KickchainApplication {

	private final Logger logger = LoggerFactory.getLogger(KickchainApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(KickchainApplication.class, args);
	}

//	@Bean
//	CommandLineRunner demo(BlockRepository repo) {
//		return args -> {
//
//			repo.deleteAll();
//
//			BlockNodeEntity block = new BlockNodeEntity();
//			block.setIndex(1);
//			block.setPreviousHash("abcdef");
//			block.setProof(100l);
//			block.setTimestamp(Instant.now());
//
//			repo.save(block);
//
//			BlockNodeEntity byIndex = repo.findByIndex(1);
//
//			logger.info("findByIndex(100): " + byIndex);
//
//
//		};
//	}

}

package de.smartsquare.kickchain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableNeo4jRepositories(basePackages = "de.smartsquare.kickchain.neo4j.repository")
@EnableScheduling
public class KickchainApplication {

	private final Logger logger = LoggerFactory.getLogger(KickchainApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(KickchainApplication.class, args);
	}

}

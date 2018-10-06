package de.smartsquare.kickchain;

import de.smartsquare.kickchain.jpa.JpaDatabaseCondition;
import de.smartsquare.kickchain.jpa.JpaService;
import de.smartsquare.kickchain.neo4j.Neo4jDatabaseCondition;
import de.smartsquare.kickchain.neo4j.Neo4jService;
import de.smartsquare.kickchain.service.DatabaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

    private final JpaService jpaService;
    private final Neo4jService neo4jService;

    public DatabaseConfig(JpaService jpaService, Neo4jService neo4jService) {
        this.jpaService = jpaService;
        this.neo4jService = neo4jService;
    }

    @Bean(name="databaseService")
    @Conditional(value= JpaDatabaseCondition.class)
    public DatabaseService getJpaDatabaseService() {
        return jpaService;
    }

    @Bean(name="databaseService")
    @Conditional(value= Neo4jDatabaseCondition.class)
    public DatabaseService getNeo4jDatabaseService() {
        return neo4jService;
    }
}

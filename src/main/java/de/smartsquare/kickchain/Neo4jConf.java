package de.smartsquare.kickchain;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("de.smartsquare.kickchain.neo4j")
@EnableNeo4jRepositories(basePackages = "de.smartsquare.kickchain.neo4j.repository")
@EnableTransactionManagement
public class Neo4jConf {

    @Value("${neo4j.uri}")
    private String uri;

    @Bean
    public SessionFactory sessionFactory() {
        return new SessionFactory(configuration(), "de.smartsquare.kickchain.neo4j.entities");
    }

    @Bean
    public org.neo4j.ogm.config.Configuration configuration() {
        return new org.neo4j.ogm.config.Configuration.Builder()
                .uri(uri)
                .build();
    }

    @Bean
    public Neo4jTransactionManager transactionManager() {
        return new Neo4jTransactionManager(sessionFactory());
    }

}
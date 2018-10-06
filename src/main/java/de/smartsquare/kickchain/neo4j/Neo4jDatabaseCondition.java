package de.smartsquare.kickchain.neo4j;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class Neo4jDatabaseCondition implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String databaseType = conditionContext.getEnvironment().getProperty("database.type", "jpa");
        return databaseType.equalsIgnoreCase("neo4j");
    }

}
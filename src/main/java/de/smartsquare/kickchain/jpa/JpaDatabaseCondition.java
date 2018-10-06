package de.smartsquare.kickchain.jpa;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class JpaDatabaseCondition implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String databaseType = conditionContext.getEnvironment().getProperty("database.type", "jpa");
        return databaseType.equalsIgnoreCase("jpa");
    }

}

package io.nbc.selectedseat.redis.distributedlock;

import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class CustomSpELParser {

    public static Object getDynamicValue(
        final String[] parameters,
        final Object[] args,
        final String name
    ) {
        SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();

        for (int i = 0; i < parameters.length; i++) {
            context.setVariable(parameters[i], args[i]);
        }

        return spelExpressionParser.parseExpression(name).getValue(context);
    }
}

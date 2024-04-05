package io.nbc.selectedseat.mail.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;

@Configuration
public class TemplateConfiguration {

    @Bean
    public TemplateEngine htmlTemplateEngine(
        final SpringResourceTemplateResolver resourceTemplateResolver
    ) {
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.addTemplateResolver(resourceTemplateResolver);
        return springTemplateEngine;
    }
}

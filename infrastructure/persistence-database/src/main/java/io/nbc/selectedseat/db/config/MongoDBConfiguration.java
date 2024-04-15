package io.nbc.selectedseat.db.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "io.nbc.selectedseat.db")
public class MongoDBConfiguration {

}

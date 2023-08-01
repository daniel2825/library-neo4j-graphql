package com.neo4j.example.graph.db.config;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;


@Configuration
@ComponentScan(basePackages = {"com.neo4j.example.graph.db"})
@EnableNeo4jRepositories(
        basePackages = "com.neo4j.example.graph.db.repository")
public class DataBaseConfig implements AutoCloseable {
    public Driver driver;
    
    //@Bean
    public Driver connectionNeo4j() {
        return driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "12345678"));
    }
    
    public static final String URL =
            System.getenv("NEO4J_URL") != null ?
                    System.getenv("NEO4J_URL") : "bllolt://localhost:7687";
    
    @Override
    public void close() throws Exception {
        driver.close();
    }
}

package com.neo4j.example.graph.db.repository;


import com.neo4j.example.graph.db.model.City;
import com.neo4j.example.graph.db.model.Country;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ICityRepository extends Neo4jRepository<City, Long> {
    @Query("MATCH (City) WHERE City.name=$nameCity RETURN City LIMIT 1")
    City consultCity(@Param("nameCity") String nameCity);
}

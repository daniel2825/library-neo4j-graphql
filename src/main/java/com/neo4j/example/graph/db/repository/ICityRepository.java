package com.neo4j.example.graph.db.repository;

import com.neo4j.example.graph.db.model.Author;
import com.neo4j.example.graph.db.model.City;
import com.neo4j.example.graph.db.model.Country;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICityRepository extends Neo4jRepository<City, Long> {
    @Query("MATCH (City) WHERE City.name=$nameCity RETURN City LIMIT 1")
    City consultCity(@Param("nameCity") String nameCity);

    @Query("MERGE (c:City {name: $nameCity})")
    City saveCity(@Param("nameCity") String nameCity);

    @Query(
            "MATCH (co:Country),(c:City) WHERE co.name=$nameCountry AND c.name=$nameCity "
                    + "MERGE (c)-[r:CITY_OF]->(co) RETURN c.name")
    void countryOfCity(
            @Param("nameCountry") String nameCountry, @Param("nameCity") String nameCity);

    @Query("MATCH (Book)-[:WROTE]-> (City) WHERE Book.name= $nameBook RETURN City")
    List<City> findCityByBookName(@Param("nameBook") String nameBook);
}

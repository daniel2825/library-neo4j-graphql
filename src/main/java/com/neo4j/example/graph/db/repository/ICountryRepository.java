package com.neo4j.example.graph.db.repository;

import com.neo4j.example.graph.db.model.Country;
import com.neo4j.example.graph.db.model.Editorial;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICountryRepository extends Neo4jRepository<Country, Long> {

    @Query("MATCH (Country) WHERE Country.name=$country RETURN Country LIMIT 1")
    Country consultCountry(@Param("country") String country);

    @Query("CREATE (c:Country {name: $country})")
    Country saveCountry(@Param("country") String country);

    @Query("MATCH (Book)-[:MADE]-> (Country) WHERE Book.name= $nameBook RETURN Country")
    List<Country>
    findCountryByBookName(@Param("nameBook") String nameBook);

}

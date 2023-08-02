package com.neo4j.example.graph.db.repository;

import com.neo4j.example.graph.db.model.Author;
import com.neo4j.example.graph.db.model.City;
import com.neo4j.example.graph.db.model.Editorial;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IEditorialRepository extends Neo4jRepository<Editorial, Long> {

    @Query("MATCH (Editorial) WHERE Editorial.name=$nameEditorial RETURN Editorial LIMIT 1")
    Editorial consultEditorial(@Param("nameEditorial") String nameEditorial);

    @Query(
            "MATCH (b:Book),(e:Editorial) WHERE b.name=$nameBook AND e.name=$nameEditorial RETURN e LIMIT 1")
    Editorial findAlreadyExistEditorialInBook(
            @Param("nameBook") String nameBook, @Param("nameEditorial") String nameEditorial);

    @Query(
            "MATCH (b:Book),(e:Editorial) WHERE b.name=$nameBook AND e.name=$nameEditorial "
                    + "MERGE (b)-[r:BUILT_BY]->(e) RETURN b.name")
    List<Editorial> bookEditorial(
            @Param("nameBook") String nameBook, @Param("nameEditorial") String nameEditorial);

    @Query(
            "MATCH (b:Book),(e:Editorial) WHERE b.name=$nameBook AND e.name=$nameEditorial "
                    + "MERGE (e)-[r:OWNER_OF]->(b) RETURN b.name")
    List<Editorial> editorialOfBook(
            @Param("nameBook") String nameBook, @Param("nameEditorial") String nameEditorial);

    @Query(
            "MATCH (c:City {name: $nameCity})-[r:IS_LOCATED]->(e:Editorial {name: $nameEditorial}) RETURN c.name LIMIT 1")
    String validateCityEditorial(
            @Param("nameEditorial") String nameEditorial, @Param("nameCity") String nameCity);

    @Query(
            "MATCH (e:Editorial),(c:City) WHERE e.name=$nameEditorial AND c.name=$nameCity "
                    + "MERGE (c)-[r:IS_LOCATED]->(e) RETURN e.name LIMIT 1")
    String cityEditorial(
            @Param("nameEditorial") String nameEditorial, @Param("nameCity") String nameCity);

    @Query("MATCH (Book)-[:BUILT_BY]-> (Editorial) WHERE Book.name= $nameBook RETURN Editorial")
    List<Editorial> findEditorialByBookName(@Param("nameBook") String nameBook);
}

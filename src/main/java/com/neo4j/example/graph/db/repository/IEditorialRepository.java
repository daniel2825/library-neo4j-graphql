package com.neo4j.example.graph.db.repository;

import com.neo4j.example.graph.db.model.Author;
import com.neo4j.example.graph.db.model.Editorial;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEditorialRepository extends Neo4jRepository<Editorial, Long> {

    @Query("MATCH (Editorial) WHERE Editorial.name=$nameEditorial RETURN Editorial LIMIT 1")
    Editorial consultEditorial(@Param("nameEditorial") String nameEditorial);

    @Query("MATCH (b:Book),(e:Editorial) WHERE b.name=$nameBook AND e.name=$nameEditorial RETURN e LIMIT 1")
    Editorial
    findAlreadyExistEditorialInBook(@Param("nameBook") String nameBook, @Param("nameEditorial") String nameEditorial);

    @Query("MATCH (b:Book),(e:Editorial) WHERE b.name=$nameBook AND e.name=$nameEditorial " +
            "CREATE (b)-[r:OWNER]->(e) RETURN b.name")
    Editorial
    editorialOfBook(@Param("nameBook") String nameBook, @Param("nameEditorial") String nameEditorial);

    @Query("MATCH (e:Editorial),(c:City) WHERE e.name=$nameEditorial AND c.name=$nameCity " +
            "CREATE (e)-[r:IS_FROM]->(c) RETURN e.name")
    List<String> editorialCity(@Param("nameEditorial") String nameEditorial, @Param("nameCity") String nameCity);


}

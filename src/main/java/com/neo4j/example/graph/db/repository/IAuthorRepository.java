package com.neo4j.example.graph.db.repository;

import com.neo4j.example.graph.db.model.Author;
import com.neo4j.example.graph.db.model.Book;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAuthorRepository extends Neo4jRepository<Author, Long> {
    Author findByName(String name);
    // there are two ways

    @Query("MATCH (Book)-[:WROTE_BY]-> (Author) WHERE Book.name= $nameBook RETURN Author")
    List<Author> findAuthorByBookName(@Param("nameBook") String nameBook);

    //    @Query("MERGE (Author: author {name: 'Sandro'})")
    @Query("MERGE (a:Author {name: $authorName, id: $id})")
    List<Author> saveAuthors(@Param("id") Long id, @Param("authorName") String authorName);

    @Query("MATCH (b:Book),(a:Author), (c:Country) WHERE b.name=$nameBook AND a.name=$nameAuthor AND c.name=$nameCountry RETURN a LIMIT 1")
    Author findAlreadyExistAuthorInBook(
            @Param("nameBook") String nameBook, @Param("nameAuthor") String nameAuthor, @Param("nameCountry") String nameCountry);

    //constraint decrepated
    @Query(
            "MATCH (a:Author),(c:Country) WHERE a.name=$nameAuthor AND c.name=$nameCountry "
                    + "MERGE (a)-[r:IS_FROM]->(c) RETURN c.name")
    List<String> authorIsFromCountry(
            @Param("nameAuthor") String nameAuthor, @Param("nameCountry") String nameCountry, @Param("id") String id);

    @Query(
            "MATCH (a:Author),(c:Country) WHERE a.name=$nameAuthor AND c.name=$nameCountry "
                    + "MERGE (c)-[r:WAS_BORN_IN]->(a) RETURN c.name")
    List<String> nationalityOfAuthors(
            @Param("nameAuthor") String nameAuthor, @Param("nameCountry") String nameCountry, @Param("id") String id);

    @Query(
            "MATCH (a:Author),(c:City) WHERE a.name=$nameAuthor AND c.name=$nameCity "
                    + "MERGE (a)-[r:LIVES]->(c) RETURN c.name")
    List<String> livesCity(
            @Param("nameAuthor") String nameAuthor, @Param("nameCity") String nameCity, @Param("id") String id);

    @Query("MATCH (a:Book) RETURN a")
    List<Author> getAllAuthors();
}

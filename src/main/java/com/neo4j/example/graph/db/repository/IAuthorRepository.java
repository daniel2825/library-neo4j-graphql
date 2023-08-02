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

  @Query("MATCH (Book)-[:WROTE]-> (Author) WHERE Book.name= $nameBook RETURN Author")
  List<Author> findAuthorByBookName(@Param("nameBook") String nameBook);

  @Query("MATCH (b:Book),(a:Author) WHERE b.name=$nameBook AND a.name=$nameAuthor RETURN a LIMIT 1")
  Author findAlreadyExistAuthorInBook(
      @Param("nameBook") String nameBook, @Param("nameAuthor") String nameAuthor);

  @Query("MATCH (a:Book) RETURN a")
  List<Author> getAllAuthors();
}

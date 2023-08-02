package com.neo4j.example.graph.db.repository;

import com.neo4j.example.graph.db.model.Book;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IBookRepository extends Neo4jRepository<Book, Long> {
  // Movie findByTitle(@Param("title") String title);

  Book findByName(String name);
  // there are two ways

  @Query("MATCH (Book)-[:WROTE]-> (Author) WHERE Book.name=$name RETURN Book")
  Book findByNameParam(@Param("name") String name);

  @Query("MATCH (b:Book),(a:Author) WHERE b.name=$nameBook AND a.name=$nameAuthor RETURN b LIMIT 1")
  Book findByNameBookAndAuthor(
      @Param("nameBook") String nameBook, @Param("nameAuthor") String nameAuthor);

  @Query("MATCH (b:Book) RETURN b")
  List<Book> getAllBooks();

  @Query(
      "MATCH (b:Book),(a:Author) WHERE b.name=$nameBook AND a.name=$nameAuthor "
          + "CREATE (b)-[r:WROTE]->(a) RETURN b.name")
  List<String> bookWasWriteByAuthor(
      @Param("nameBook") String nameBook, @Param("nameAuthor") String nameAuthor);

  @Query(
      "MATCH (b:Book),(a:Author) WHERE b.name=$nameBook AND a.name=$nameAuthor "
          + "CREATE (a)-[r:WROTE]->(b) RETURN b.name")
  List<String> authorWroteBook(
      @Param("nameBook") String nameBook, @Param("nameAuthor") String nameAuthor);

  @Query(
      "MATCH (b:Book),(c:Country) WHERE b.name=$nameBook AND c.name=$nameCountry "
          + "CREATE (b)-[r:MADE]->(c) RETURN b.name")
  List<String> bookWasMadeInCountry(
      @Param("nameBook") String nameBook, @Param("nameCountry") String nameCountry);

  @Query(
      "MATCH (b:Book),(c:Country) WHERE b.name=$nameBook AND c.name=$nameCountry "
          + "CREATE (c)-[r:MADE]->(b) RETURN b.name")
  List<String> countryHasBook(
      @Param("nameBook") String nameBook, @Param("nameCountry") String nameCountry);

  @Query("CREATE (:Book {name:$name, pageCount:$pageCount, year:$year })")
  // must be primitive
  // We can't do make it  @Query("CREATE (b:Book $book) RETURN b") use method save of
  // neo4j-repository
  Book saveBookParams(
      @Param("name") String name, @Param("year") String year, @Param("pageCount") String pageCount);
}

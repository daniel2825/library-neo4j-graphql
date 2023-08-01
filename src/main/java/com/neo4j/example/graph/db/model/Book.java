package com.neo4j.example.graph.db.model;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node
// adjust name according to documentation
public class Book {
  @Id @GeneratedValue private Long id;
  private String name;
  private String pageCount;
  private String year;
  /*
  @Relationship(type = "WRITES", direction = Relationship.Direction.INCOMING)
  private List<Author> author;*/

  public Book(String name, String pageCount, String year) {
    this.name = name;
    this.pageCount = pageCount;
    this.year = year;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPageCount() {
    return pageCount;
  }

  public void setPageCount(String pageCount) {
    this.pageCount = pageCount;
  }

  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }
}

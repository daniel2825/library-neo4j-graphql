package com.neo4j.example.graph.db.controller;

import com.neo4j.example.graph.db.model.*;
import com.neo4j.example.graph.db.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Controller
public class BookController {

  @Autowired private BookServices bookServices;

  @Autowired private AuthorServices authorServices;

  @Autowired private EditorialServices editorialServices;

  @Autowired private CountryServices countryServices;

  @Autowired private CityServices cityServices;

  @QueryMapping
  public Book bookByName(@Argument String name) {
    return bookServices.getByName(name);
  }

  @MutationMapping
  @Transactional
  public Book addBook(
      @Argument Book book,
      @Argument List<Author> authors,
      @Argument Country country,
      @Argument Editorial editorial) {
    return bookServices.saveBook(book, authors, country, editorial);
  }

  @MutationMapping
  public Author addAuthor(@Argument Author author) {
    return authorServices.save(author);
  }

  @SchemaMapping(field = "author")
  public List<Author> findAuthor(Book book) {
    return authorServices.getAuthor(book.getName());
  }

  @SchemaMapping()
  public List<Editorial> editorial(Book book) {
    return editorialServices.getEditorial(book.getName());
  }

  @SchemaMapping()
  public List<City> city(Book book) {
    return cityServices.getCity(book.getName());
  }

  @SchemaMapping()
  public List<Country> country(Book book) {
    return countryServices.getCountry(book.getName());
  }
  /*
  two way to consult author
  public Author author(Book book) {
      return authorServices.getAuthor(book.getName());
  }*/
  /*
  @QueryMapping
  public author findAuthorById(@Argument String id) {
      return authorServices.getById(id);
  }
  */
}

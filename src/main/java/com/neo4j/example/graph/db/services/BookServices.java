package com.neo4j.example.graph.db.services;

import com.neo4j.example.graph.db.component.AuthorComponent;
import com.neo4j.example.graph.db.component.BookComponent;
import com.neo4j.example.graph.db.component.EditorialComponent;
import com.neo4j.example.graph.db.config.DataBaseConfig;
import com.neo4j.example.graph.db.model.*;
import com.neo4j.example.graph.db.repository.*;
import org.neo4j.driver.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookServices {

  private final IBookRepository bookRepository;
  private final BookComponent bookComponent;
  private final EditorialComponent editorialComponent;
  private final IAuthorRepository authorRepository;
  private final CountryServices countryServices;
  private final AuthorServices authorServices;
  private final CityServices cityServices;
  private final EditorialServices editorialServices;
  private final IEditorialRepository editorialRepository;
  private final AuthorComponent authorComponent;
  private final DataBaseConfig dataBaseConfig;

  @Autowired
  public BookServices(
      IBookRepository bookRepository,
      BookComponent bookComponent,
      EditorialComponent editorialComponent,
      IAuthorRepository authorRepository,
      AuthorServices authorServices,
      EditorialServices editorialServices,
      final DataBaseConfig dataBaseConfig,
      CountryServices countryServices,
      IEditorialRepository editorialRepository,
      CityServices cityServices,
      AuthorComponent authorComponent) {
    this.bookRepository = bookRepository;
    this.bookComponent = bookComponent;
    this.editorialComponent = editorialComponent;
    this.authorRepository = authorRepository;
    this.authorServices = authorServices;
    this.editorialServices = editorialServices;
    this.dataBaseConfig = dataBaseConfig;
    this.countryServices = countryServices;
    this.editorialRepository = editorialRepository;
    this.cityServices = cityServices;
    this.authorComponent = authorComponent;
  }

  public Book saveBook(Book book, List<Author> authors, Country country, Editorial editorial) {
    bookRepository.save(book);
    return book;
  }

  public Book getByName(String name) {

    /*  Result result3 = dataBaseConfig.connectionNeo4j()
            .session()
            .run("MATCH (Book {name: 'Don Quijote de la Mancha'}) RETURN Book.pageCount, Book.editorial, Book.year");
    System.out.println(result3.single().get(0).asString());
    //return books.stream().filter(book -> book.getId().equals(id)).findFirst().orElse(null);¨*/
    return bookRepository.findByName(name);
  }

  public Book getByNameBook(String id) {
    DataBaseConfig dataBaseConfig1 = new DataBaseConfig();
    Result result2 =
        dataBaseConfig1
            .connectionNeo4j()
            .session()
            .run("MATCH (book {name: 'cien años de soledad'}) RETURN book.pageCount, book.year");

    System.out.println(result2.single().get(0));
    while (result2.hasNext()) {
      System.out.println(result2.next().values());
    }
    // result2.stream().forEach(x -> System.out.println(x));
    return new Book("2l", "4", "3");

    // return books.stream().filter(book -> book.getId().equals(id)).findFirst().orElse(null);
  }
}

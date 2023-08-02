package com.neo4j.example.graph.db.services;

import com.neo4j.example.graph.db.component.AuthorComponent;
import com.neo4j.example.graph.db.model.Author;
import com.neo4j.example.graph.db.model.Book;
import com.neo4j.example.graph.db.repository.IAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServices {

  @Autowired private IAuthorRepository authorRepository;
  @Autowired private AuthorComponent authorComponent;

  public List<Author> getAuthor(String id) {
    return authorRepository.findAuthorByBookName(id);
  }

  public Author save(Author author) {
    return authorRepository.save(author);
  }

  public List<Author> saveAll(List<Author> authors, Book book) {
    List<Author> listToSave = authorComponent.authorsToSaveInDb(authors, book);

    final List<Author> authorsNewToSave =
        listToSave.stream()
            .filter(x -> authorRepository.findByName(x.getName()) == null)
            .collect(Collectors.toList());

    return authorRepository.saveAll(authorsNewToSave);
  }
}

package com.neo4j.example.graph.db.controller;

import com.neo4j.example.graph.db.model.*;
import com.neo4j.example.graph.db.services.AuthorServices;
import com.neo4j.example.graph.db.services.BookServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookServices bookServices;

    @Autowired
    private AuthorServices authorServices;

    @QueryMapping
    public Book bookByName(@Argument String name) {
        return bookServices.getByName(name);
    }

    @MutationMapping
    public Book addBookAndAuthor(
            @Argument Book book, @Argument List<Author> authors, @Argument Country country, @Argument Editorial editorial, @Argument City city) {
        return bookServices.saveBookAuthor(book, authors, country, editorial, city);
    }

    @MutationMapping
    public Author addAuthor(@Argument Author author) {
        return authorServices.save(author);
    }

    @SchemaMapping(field = "author")
    public List<Author> findAuthor(Book book) {
        return authorServices.getAuthor(book.getName());
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

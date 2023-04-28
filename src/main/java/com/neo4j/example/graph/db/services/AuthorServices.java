package com.neo4j.example.graph.db.services;


import com.neo4j.example.graph.db.model.Author;
import com.neo4j.example.graph.db.repository.IAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServices {

    @Autowired
    private IAuthorRepository authorRepository;

    public List<Author> getAuthor(String id) {
        return authorRepository.findAuthorByBookName(id);
    }

    public Author save(Author author) {
        return authorRepository.save(author);
    }
}

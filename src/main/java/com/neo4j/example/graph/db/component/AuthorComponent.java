package com.neo4j.example.graph.db.component;

import com.neo4j.example.graph.db.model.*;
import com.neo4j.example.graph.db.repository.IAuthorRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.neo4j.example.graph.db.services.CityServices;
import com.neo4j.example.graph.db.services.CountryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorComponent {

    private final IAuthorRepository authorRepository;

    @Autowired
    public AuthorComponent(IAuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> authorsExistInDb(Book book, List<Author> authors) {
        return authors.stream()
                .map(authorItem -> findAuthor(book, authorItem))
                .filter(x -> x.getName() != null)
                .collect(Collectors.toList());
    }

    public Author findAuthor(Book book, Author authorItem) {
        return Optional.ofNullable(
                        authorRepository.findAlreadyExistAuthorInBook(book.getName(), authorItem.getName(), authorItem.getCountry().getName()))
                .orElse(new Author());
    }

    public List<Author> authorsToSaveInDb(final List<Author> inAuthors, final Book book) {

        final List<Author> authorsToFilter = inAuthors;
        authorsToFilter.addAll(authorsExistInDb(book, inAuthors));

        return authorsFilteredToSaveInDb(authorsToFilter);
    }

    public List<Author> authorsFilteredToSaveInDb(List<Author> authorsToFilter) {
        final List<String> nameAuthorsExistInDb =
                authorsToFilter.stream().map(itemName -> itemName.getName()).collect(Collectors.toList());

        authorsToFilter.removeIf(
                itemAuthor -> Collections.frequency(nameAuthorsExistInDb, itemAuthor.getName()) > 1);

        final List<Author> authorsFiltered = authorsToFilter;

        return authorsFiltered;
    }

    public void authorIsFromCountry(List<Author> authors) {

        authors.forEach(author ->
                authorRepository.authorIsFromCountry(author.getName(), author.getCountry().getName(), author.getId().toString()));
    }

    public void nationalityOfAuthors(List<Author> authors) {

        authors.forEach(author ->
                authorRepository.nationalityOfAuthors(author.getName(), author.getCountry().getName(), author.getId().toString()));
    }

    public void livesCity(List<Author> authors) {

        authors.forEach(author ->
                authorRepository.livesCity(author.getName(), author.getCity().getName(), author.getId().toString()));
    }
}

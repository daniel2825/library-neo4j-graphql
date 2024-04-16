package com.neo4j.example.graph.db.component;

import com.neo4j.example.graph.db.model.Author;
import com.neo4j.example.graph.db.model.Book;
import com.neo4j.example.graph.db.repository.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookComponent {
    @Autowired
    private final IBookRepository bookRepository;

    public BookComponent(IBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void bookWasWroteByAuthorRelation(Book book, List<Author> authors) {
        authors.forEach(a -> bookRepository.bookWasWroteByAuthor(book.getName(), a.getName()));
    }

    public void authorWroteBookRelation(Book book, List<Author> authors) {
        authors.forEach(a -> bookRepository.authorWroteBook(book.getName(), a.getName()));
    }

    public void bookWasMadeInCountry(String nameBook, String nameCountry) {
        bookRepository.bookWasMadeInCountry(nameBook, nameCountry);
    }

    public void countryHasBook(String nameBook, String nameCountry) {
        bookRepository.countryHasBook(nameBook, nameCountry);
    }
}

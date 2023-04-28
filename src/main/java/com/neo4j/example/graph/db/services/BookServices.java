package com.neo4j.example.graph.db.services;

import com.neo4j.example.graph.db.config.DataBaseConfig;
import com.neo4j.example.graph.db.model.*;
import com.neo4j.example.graph.db.repository.*;
import org.neo4j.driver.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServices {

    private final IBookRepository bookRepository;
    private final IAuthorRepository authorRepository;
    private final ICountryRepository countryRepository;
    private final ICityRepository cityRepository;
    private final IEditorialRepository editorialRepository;
    private final DataBaseConfig dataBaseConfig;

    @Autowired
    public BookServices(
            IBookRepository bookRepository,
            IAuthorRepository authorRepository,
            final DataBaseConfig dataBaseConfig,
            ICountryRepository countryRepository,
            IEditorialRepository editorialRepository,
            ICityRepository cityRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.dataBaseConfig = dataBaseConfig;
        this.countryRepository = countryRepository;
        this.editorialRepository = editorialRepository;
        this.cityRepository = cityRepository;
    }

    public Book saveBookAuthor(Book book, List<Author> authors, Country country, Editorial editorial, City city) {

        final List<Author> authorsExistInDb = authors.stream()
                .map(authorItem ->
                        Optional.ofNullable(
                                        authorRepository
                                                .findAlreadyExistAuthorInBook(
                                                        book.getName(),
                                                        authorItem.getName()))
                                .orElse(new Author()))
                .filter(x -> x.getName() != null)
                .collect(Collectors.toList());

        final List<Author> authorsToSave = authors;
        authorsToSave.addAll(authorsExistInDb);
        authorsExistInDb.clear();

        final List<String> nameAuthorsExistInDb =
                authorsToSave.stream()
                        .map(itemName -> itemName.getName())
                        .collect(Collectors.toList());

        authorsToSave.removeIf(itemAuthor -> Collections.frequency(nameAuthorsExistInDb, itemAuthor.getName()) > 1);

        Optional.ofNullable(countryRepository.consultCountry(country.getName()))
                .orElseGet(() -> countryRepository.save(country));

        Optional.ofNullable(cityRepository.consultCity(city.getName()))
                .orElseGet(() -> cityRepository.save(city));

        authorRepository.saveAll(authorsToSave);

        final Optional<Editorial> consultEditorialBook = Optional.ofNullable(editorialRepository.findAlreadyExistEditorialInBook(book.getName(), editorial.getName()));

        if (consultEditorialBook.isEmpty()) {
            final Book resultBook = bookRepository.save(book);
            editorialRepository.save(editorial);
            editorialRepository.editorialOfBook(book.getName(), editorial.getName());
            editorialRepository.editorialCity(editorial.getName(), city.getName());
        }

        authorsToSave.forEach(a -> bookRepository.bookWasWriteByAuthor(book.getName(), a.getName()));
        bookRepository.bookWasMadeInCountry(book.getName(), country.getName());
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
                        .run(
                                "MATCH (book {name: 'cien años de soledad'}) RETURN book.pageCount, book.year");

        System.out.println(result2.single().get(0));
        while (result2.hasNext()) {
            System.out.println(result2.next().values());
        }
        // result2.stream().forEach(x -> System.out.println(x));
        return new Book("2l", "4", "3");

        // return books.stream().filter(book -> book.getId().equals(id)).findFirst().orElse(null);
    }
}

package com.neo4j.example.graph.db.component;

import com.neo4j.example.graph.db.model.*;
import com.neo4j.example.graph.db.repository.IAuthorRepository;
import com.neo4j.example.graph.db.services.AuthorServices;
import com.neo4j.example.graph.db.services.CityServices;
import com.neo4j.example.graph.db.services.CountryServices;
import com.neo4j.example.graph.db.services.EditorialServices;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class BookAspectComponent {

    private final IAuthorRepository authorRepository;
    private final BookComponent bookComponent;
    private final CityServices cityServices;
    private final CountryServices countryServices;
    private final EditorialComponent editorialComponent;

    private final AuthorComponent authorComponent;
    private final EditorialServices editorialServices;
    private final AuthorServices authorServices;

    @Autowired
    public BookAspectComponent(
            IAuthorRepository authorRepository,
            BookComponent bookComponent,
            CityServices cityServices,
            CountryServices countryServices,
            EditorialComponent editorialComponent,
            AuthorComponent authorComponent, EditorialServices editorialServices,
            AuthorServices authorServices) {
        this.authorRepository = authorRepository;
        this.bookComponent = bookComponent;
        this.cityServices = cityServices;
        this.countryServices = countryServices;
        this.editorialComponent = editorialComponent;
        this.authorComponent = authorComponent;
        this.editorialServices = editorialServices;
        this.authorServices = authorServices;
    }

    @Pointcut("execution(* com.neo4j.example.graph.db.services.BookServices.saveBook(..))")
    private void saveBook() {
    }

    @Before(value = "saveBook() and args(book,authors,country,editorial)")
    public void validationBeforeSaveBook(
            JoinPoint joinPoint, Book book, List<Author> authors, Editorial editorial, Country country)
            throws Throwable {

        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        System.out.println("Received request in " + className + " - Method: " + methodName);

        editorialComponent.findBookWroteBeforeByEditorial(book.getName(), editorial.getName());
        editorialServices.saveEditorial(editorial);
        cityServices.saveCity(editorial.getCity());
        countryServices.saveCountry(country);
        authorServices.saveAll(authors, book);
    }

    @After(
            value =
                    "execution(* com.neo4j.example.graph.db.services.BookServices.saveBook(..)) and args(book,authors,country,editorial)")
    public void validateAfterSaveBook5(
            Book book, List<Author> authors, Editorial editorial, Country country) {

        editorialComponent.bookEditorialRelation(book.getName(), editorial.getName());
        editorialComponent.editorialOfBookRelation(book.getName(), editorial.getName());
        editorialComponent.cityOfEditorialRelation(editorial.getName(), editorial.getCity().getName());
        bookComponent.bookWasWroteByAuthorRelation(book, authors);
        bookComponent.bookWasMadeInCountry(book.getName(), country.getName());
        bookComponent.countryHasBook(book.getName(), country.getName());
        authorComponent.authorIsFromCountry(authors);
        authorComponent.nationalityOfAuthors(authors);
        authorComponent.livesCity(authors);

        cityServices.getAllCities().forEach(city -> cityServices.countryOfCity(city.getName()));

        bookComponent.authorWroteBookRelation(book, authors);

    }
}

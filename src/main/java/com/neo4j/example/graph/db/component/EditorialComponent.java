package com.neo4j.example.graph.db.component;

import com.neo4j.example.graph.db.model.Editorial;
import com.neo4j.example.graph.db.repository.IEditorialRepository;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.module.FindException;
import java.util.Optional;

@Component
public class EditorialComponent {

    @Autowired
    private final IEditorialRepository editorialRepository;

    public EditorialComponent(IEditorialRepository editorialRepository) {
        this.editorialRepository = editorialRepository;
    }

    public void findBookWroteBeforeByEditorial(String bookName, String editorialName) {
        Try.of(() -> editorialRepository.findAlreadyExistEditorialInBook(bookName, editorialName))
                .onSuccess(
                        res -> {
                            Optional.ofNullable(res)
                                    .ifPresent(
                                            x -> {
                                                System.out.println("Book already exist with this editorial");
                                                throw new FindException();
                                            });
                        });
    }

    public void bookEditorialRelation(String bookName, String nameEditorial) {
        editorialRepository.bookEditorial(bookName, nameEditorial);
    }

    public void editorialOfBookRelation(String bookName, String nameEditorial) {
        editorialRepository.editorialOfBook(bookName, nameEditorial);
    }

    public void cityOfEditorialRelation(String editorialName, String cityNameEditorial) {
        Optional.ofNullable(editorialRepository.validateCityEditorial(editorialName, cityNameEditorial))
                .orElseGet(() -> editorialRepository.cityEditorial(editorialName, cityNameEditorial));
    }
}

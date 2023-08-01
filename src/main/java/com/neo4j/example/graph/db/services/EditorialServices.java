package com.neo4j.example.graph.db.services;

import com.neo4j.example.graph.db.model.Editorial;
import com.neo4j.example.graph.db.repository.IEditorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EditorialServices {
  @Autowired private IEditorialRepository editorialRepository;

  public List<Editorial> getEditorial(String nameBook) {
    return editorialRepository.findEditorialByBookName(nameBook);
  }

  public void saveEditorial(Editorial editorial) {
    Optional.ofNullable(editorialRepository.consultEditorial(editorial.getName()))
        .orElseGet(() -> editorialRepository.save(editorial));
  }
}

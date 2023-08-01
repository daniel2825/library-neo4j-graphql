package com.neo4j.example.graph.db.services;

import com.neo4j.example.graph.db.model.Author;
import com.neo4j.example.graph.db.model.Country;
import com.neo4j.example.graph.db.repository.IAuthorRepository;
import com.neo4j.example.graph.db.repository.ICountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryServices {
  @Autowired private ICountryRepository countryRepository;

  public List<Country> getCountry(String nameBook) {
    return countryRepository.findCountryByBookName(nameBook);
  }

  public void saveCountry(Country country) {
    Optional.ofNullable(countryRepository.consultCountry(country.getName()))
        .orElseGet(() -> countryRepository.save(country));
  }
}

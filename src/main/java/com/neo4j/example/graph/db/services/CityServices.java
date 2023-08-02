package com.neo4j.example.graph.db.services;

import com.neo4j.example.graph.db.model.Author;
import com.neo4j.example.graph.db.model.City;
import com.neo4j.example.graph.db.repository.IAuthorRepository;
import com.neo4j.example.graph.db.repository.ICityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityServices {
  @Autowired private ICityRepository cityRepository;

  public List<City> getCity(String nameBook) {
    return cityRepository.findCityByBookName(nameBook);
  }

  public void saveCity(City city) {
    Optional.ofNullable(cityRepository.consultCity(city.getName()))
        .orElseGet(() -> cityRepository.save(city));
  }
}

package com.neo4j.example.graph.db.model;

import org.springframework.data.neo4j.core.schema.*;

@Node
public class Editorial {
  @Id @GeneratedValue private Long id;
  private String name;
  private String address;
  private String email;

  // This other way to make relationship with only use save data saveBook example
  @Relationship(type = "IS_FROM", direction = Relationship.Direction.OUTGOING)
  private City city;

  public City getCity() {
    return city;
  }

  public void setCity(City city) {
    this.city = city;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}

package com.neo4j.example.graph.db.config;

import java.util.Arrays;
import java.util.Optional;

public enum CountryCity {

    MEDELLIN("Medellín", "Colombia"),
    BOGOTA("Bogotá", "Colombia"),
    BUENOS_AIRES("Buenos Aires", "Argentina"),
    MADRID("Madrid", "España"),
    LA_PLATA("La plata", "Argentina");
    private String city;
    private String country;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    CountryCity(String city, String country) {
        this.city = city;
        this.country = country;
    }

    public static Optional<CountryCity> countryOfCity(final String city) {
        return Arrays.stream(CountryCity.values())
                .filter(x -> x.city.equals(city))
                .findFirst();
    }
}

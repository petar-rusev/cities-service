package com.sap.cloud.cities.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Data
@Entity
public class City implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Double area;

    private Integer population;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return
                Objects.equals(name, city.name) &&
                Objects.equals(area, city.area) &&
                Objects.equals(population, city.population);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, area, population);
    }
}
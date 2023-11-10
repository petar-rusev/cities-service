package com.sap.cloud.cities.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class City {
    @GeneratedValue
    @Id
    private Long id;

    private String name;

    private Double area;

    private Integer population;

    private Double density;
}

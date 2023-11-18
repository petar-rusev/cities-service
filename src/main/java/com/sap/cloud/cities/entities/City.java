package com.sap.cloud.cities.entities;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
public class City {
    @GeneratedValue
    @Id
    private Long id;

    private String name;

    private Double area;

    private Integer population;
}

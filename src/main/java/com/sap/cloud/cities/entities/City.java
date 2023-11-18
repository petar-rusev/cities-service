package com.sap.cloud.cities.entities;


import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.apache.commons.lang3.builder.HashCodeExclude;

import static org.eclipse.jdt.internal.compiler.codegen.ConstantPool.HashCode;

@Data
@Entity
public class City {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Double area;

    private Integer population;
}

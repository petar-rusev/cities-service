package com.sap.cloud.cities.repository;

import com.sap.cloud.cities.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CityRepository extends JpaRepository<City, Long>, QuerydslPredicateExecutor<City> {
}

package com.airbnb.repository;

import com.airbnb.entity.Stops;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StopsRepository extends JpaRepository<Stops, Long> {
}
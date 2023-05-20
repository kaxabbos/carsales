package com.carsales.repo;

import com.carsales.models.Dealerships;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoDealerships extends JpaRepository<Dealerships, Long> {
}

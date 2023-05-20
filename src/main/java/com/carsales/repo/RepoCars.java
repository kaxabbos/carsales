package com.carsales.repo;

import com.carsales.models.Cars;
import com.carsales.models.enums.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepoCars extends JpaRepository<Cars, Long> {
    List<Cars> findAllByBrand(Brand brand);

    List<Cars> findAllByNameContaining(String name);

    List<Cars> findAllByYear(int year);
}

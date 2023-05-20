package com.carsales.repo;

import com.carsales.models.Carts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoCarts extends JpaRepository<Carts, Long> {
}

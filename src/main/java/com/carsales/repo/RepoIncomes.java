package com.carsales.repo;

import com.carsales.models.Income;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoIncomes extends JpaRepository<Income, Long> {
}

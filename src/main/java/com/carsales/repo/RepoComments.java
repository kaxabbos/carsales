package com.carsales.repo;

import com.carsales.models.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoComments extends JpaRepository<Comments, Long> {
}

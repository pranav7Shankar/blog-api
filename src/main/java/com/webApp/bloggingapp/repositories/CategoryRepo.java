package com.webApp.bloggingapp.repositories;

import com.webApp.bloggingapp.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {
    Optional<Category> findByCategoryTitleIgnoreCase(String categoryTitle);
}

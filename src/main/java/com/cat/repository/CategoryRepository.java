package com.cat.repository;

import com.cat.model.dao.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
//    Category getCategoryByName(String name);
   Category findByName(String name);

}

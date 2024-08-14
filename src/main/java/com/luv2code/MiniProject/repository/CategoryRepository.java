package com.luv2code.MiniProject.repository;

import com.luv2code.MiniProject.entity.Category;
import com.luv2code.MiniProject.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);
}

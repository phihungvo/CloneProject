package com.luv2code.MiniProject.repository;

import com.luv2code.MiniProject.entity.Role;
import com.luv2code.MiniProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}

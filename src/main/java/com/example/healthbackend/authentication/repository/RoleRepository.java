package com.example.healthbackend.authentication.repository;


import com.example.healthbackend.authentication.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long > {

    Role findByName(String name);
    Boolean existsByName(String roleName);

}

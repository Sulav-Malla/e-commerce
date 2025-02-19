package com.sulav.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sulav.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {

}

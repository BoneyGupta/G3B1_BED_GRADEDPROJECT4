package com.gl.ems2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gl.ems2.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}

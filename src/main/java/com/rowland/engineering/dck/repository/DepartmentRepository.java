package com.rowland.engineering.dck.repository;

import com.rowland.engineering.dck.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {

    Department findByName(String dept);

}

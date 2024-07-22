package com.rowland.engineering.dck.repository;

import com.rowland.engineering.dck.model.Department;
import com.rowland.engineering.dck.model.DepartmentName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {

    Optional<Department> findByDepartmentName(String dept);
    Department findByDepartmentName(DepartmentName dept);


}

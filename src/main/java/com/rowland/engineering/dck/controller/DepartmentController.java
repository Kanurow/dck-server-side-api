package com.rowland.engineering.dck.controller;

import com.rowland.engineering.dck.model.Department;
import com.rowland.engineering.dck.service.IDepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/department")
@Tag(name = "Department")
@RequiredArgsConstructor
public class DepartmentController {

    private final IDepartmentService departmentService;

    @Operation(
            description = "Returns list of all departments",
            summary = "Returns all departments"
    )
    @GetMapping("/all-departments")
    public List<Department> getAllDepartment() {
        return departmentService.getAllDepartments();
    }
}

package com.rowland.engineering.dck.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

@NoArgsConstructor
@Data
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
    private DepartmentName departmentName;

    @JsonIgnore
    @ManyToMany(mappedBy = "department")
    private Collection<User> users = new HashSet<>();

    public Department(DepartmentName departmentName) {
        this.departmentName = departmentName;
    }

}

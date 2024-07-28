package com.rowland.engineering.dck.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Data
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NaturalId
    private String name;


    @JsonIgnore
    @ManyToMany(mappedBy = "departments")
    private Collection<User> users = new HashSet<>();

    public Department(String name) {
        this.name = name;
    }

    public void addUserToDepartment(User user){
        user.getDepartments().add(this);
        this.getUsers().add(user);
    }


    public void removeUserFromDepartment(User user){
        user.getDepartments().remove(this);
        this.getUsers().remove(user);
    }

    public void removeAllUsersFromDepartment(){
        if (this.getUsers() != null){
            List<User> roleUsers = this.getUsers().stream().toList();
            roleUsers.forEach(this :: removeUserFromDepartment);
        }
    }
    public String getName(){
        return name != null ? name : "";
    }

}

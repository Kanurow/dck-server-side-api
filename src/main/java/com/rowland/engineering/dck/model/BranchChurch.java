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
@Table(name = "branch_church_table")
public class BranchChurch {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NaturalId
    private String name;


    @JsonIgnore
    @ManyToMany(mappedBy = "branchChurch")
    private Collection<User> users = new HashSet<>();


    public BranchChurch(String name) {
        this.name = name;
    }

    public void addUserToBranchChurch(User user){
        user.getBranchChurch().add(this);
        this.getUsers().add(user);
    }


    public void removeUserFromBranchChurch(User user){
        user.getBranchChurch().remove(this);
        this.getUsers().remove(user);
    }
    public void removeAllUsersFromBranchChurch(){
        if (this.getUsers() != null){
            List<User> roleUsers = this.getUsers().stream().toList();
            roleUsers.forEach(this :: removeUserFromBranchChurch);
        }
    }

    public  String getName(){
        return name != null? name : "";
    }
}

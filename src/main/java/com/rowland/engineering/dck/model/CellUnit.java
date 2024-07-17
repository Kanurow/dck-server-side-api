package com.rowland.engineering.dck.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Builder
@Entity
@AllArgsConstructor
public class CellUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @NotBlank
    private String cellName;
    private String cellLongitude;
    private String cellLatitude;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "cell_units_table",
            joinColumns = @JoinColumn(name = "cell_unit_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> cellMembers = new HashSet<>();

    private Long cellLeaderId;
    private String cellLeadersPhoneNumber;
    private String cellAddress;
}

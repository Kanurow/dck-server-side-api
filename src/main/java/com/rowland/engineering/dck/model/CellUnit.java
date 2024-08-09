package com.rowland.engineering.dck.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NaturalId;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
public class CellUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NaturalId
    @NotBlank
    private String name;

    private int longitude;
    private int latitude;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "cell_units_table",
            joinColumns = @JoinColumn(name = "cell_unit_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> members = new HashSet<>();

    @Column(name = "leader_id")
    private UUID leaderId;
    @Column(name = "leaders_phone_number")
    private String leadersPhoneNumber;
    private String address;

    @Override
    public int hashCode() {
        return Objects.hash(id, name, longitude, latitude);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellUnit cellUnit = (CellUnit) o;
        return Objects.equals(id, cellUnit.id) &&
                Objects.equals(name, cellUnit.name) &&
                Objects.equals(longitude, cellUnit.longitude) &&
                Objects.equals(latitude, cellUnit.latitude);
    }
}

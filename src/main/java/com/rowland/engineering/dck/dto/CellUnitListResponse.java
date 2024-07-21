package com.rowland.engineering.dck.dto;

import com.rowland.engineering.dck.model.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CellUnitListResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @NaturalId
    @NotBlank
    private String cellName;

    private short numberOfCellMembers;
    private String cellLeadersName;
    private int cellLeadersPhoneNumber;
    private double longitude;
    private double latitude;

    @OneToMany(mappedBy = "cell_unit")
    private List<User> cellMembers = new ArrayList<>();

}
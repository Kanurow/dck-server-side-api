package com.rowland.engineering.dck.dto;

import com.rowland.engineering.dck.model.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCellUnitsResponse {
    private UUID id;
    private String name;

    private String longitude;
    private String latitude;

    private Set<UserSummary> members = new HashSet<>();

    private UUID leaderId;
    private String leadersPhoneNumber;
    private String address;


}

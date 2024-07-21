package com.rowland.engineering.dck.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSummary {
    private UUID id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
}


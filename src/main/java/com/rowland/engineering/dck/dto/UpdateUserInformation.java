package com.rowland.engineering.dck.dto;

import com.rowland.engineering.dck.model.Gender;
import lombok.Data;
import java.time.LocalDate;

@Data
public class UpdateUserInformation {
    private String firstName;
    private String lastName;
    private String alternativePhoneNumber;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String favouriteBiblePassage;
}

package com.rowland.engineering.dck.model;

import com.rowland.engineering.dck.model.audit.DateAudit;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.internal.util.stereotypes.Lazy;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@Builder
@Entity
@AllArgsConstructor
@Table(name = "users_table", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "phoneNumber"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
public class User extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(min = 2,max = 25)
    private String firstName;
    @NotBlank
    @Size(min = 2,max = 25)
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotBlank
    private String branchChurch;

    @PastOrPresent(message = "Date of birth cannot be in future")
    @Temporal(TemporalType.DATE)
    private LocalDate dateOfBirth;

    @NaturalId
    @Email(message = "Must be a valid email address")
    private String email;

    @NaturalId
    @NotBlank
    private String phoneNumber;

    private String alternativePhoneNumber;

    @Column(name = "is_cell_leader")
    private boolean isCellLeader;
    @NotBlank
    private String password;

    private String favouriteBiblePassage;
    private String profileImageUrl;
    private Double walletBalance;

    @Lazy
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    private Long cellUnitId;

    public User(String firstName, String lastName, LocalDate dateOfBirth,
                String email, String phoneNumber, String branchChurch,Gender gender, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.branchChurch = branchChurch;
        this.gender = gender;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(email, user.email) &&
                Objects.equals(phoneNumber, user.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, phoneNumber);
    }
}

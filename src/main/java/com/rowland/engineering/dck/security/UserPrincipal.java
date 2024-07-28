package com.rowland.engineering.dck.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rowland.engineering.dck.model.Gender;
import com.rowland.engineering.dck.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor
public class UserPrincipal implements UserDetails, Principal {
    private UUID id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String branchChurch;

    private String phoneNumber;

    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;
    public UserPrincipal(UUID id, String firstName, String lastName, String phoneNumber, LocalDate dateOfBirth, Gender gender, String email,
                         String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }
    public UserPrincipal(UUID id) {
        this.id = id;
    }


    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName())
        ).collect(Collectors.toList());

        return new UserPrincipal(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getDateOfBirth(),
                user.getGender(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String getName() {
        return firstName;
    }
}
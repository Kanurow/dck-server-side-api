package com.rowland.engineering.dck.security;


import com.rowland.engineering.dck.model.User;
import com.rowland.engineering.dck.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String phoneNumberOrEmail)
            throws UsernameNotFoundException {
        User user = userRepository.findByPhoneNumberOrEmail(phoneNumberOrEmail, phoneNumberOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with phone number or email : " + phoneNumberOrEmail)
                );

        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );
        return UserPrincipal.create(user);
    }
}



package com.andromeda.foodcare.service;

import static java.util.Collections.singletonList;

import com.andromeda.foodcare.exceptions.UserException;
import com.andromeda.foodcare.model.User;
import com.andromeda.foodcare.repository.UserRepository;
import java.util.Collection;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class responsible for configuring user-specific data, that will be used by Spring Security.
 */
@Service
@AllArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> UserException.userNotFound(email));

        return new org.springframework.security.core.userdetails.User(
            user.getEmail(), user.getPassword(), true, true, true,
            true, getAuthorities(user.getRole().toString()));
    }

    private Collection<GrantedAuthority> getAuthorities(String role) {
        return singletonList(new SimpleGrantedAuthority(role));
    }
}

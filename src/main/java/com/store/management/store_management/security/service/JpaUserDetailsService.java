package com.store.management.store_management.security.service;

import com.store.management.store_management.repository.UserRepository;
import com.store.management.store_management.security.model.SecurityUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private static final String USER_NOT_FOUND_MSG = "User does not exist for the given username";

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_MSG));
    }
}

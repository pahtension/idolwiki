package com.example.idolwiki.model.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class AuthUserDetailsService implements UserDetailsService {
    private final AuthMapper authMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin byId = authMapper.findById(username);
        if (byId == null){
            throw new UsernameNotFoundException("user not found");
        }

        return new User(byId.getId(), byId.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }
}

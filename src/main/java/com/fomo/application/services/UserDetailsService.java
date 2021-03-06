package com.fomo.application.services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.fomo.application.entity.User;
import com.fomo.application.entity.UserRole;
import com.fomo.application.repository.UserRepository;
import com.fomo.application.repository.UserRolesRepository;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private UserRolesRepository userRolesRepo;
    
    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);

        User user = userRepo.findByEmail(login);
        if (user == null) {
            throw new UsernameNotFoundException("User " + login + " was not found in the database");
        } 
        //else if (!user.getEnabled()) {
       //     throw new UserNotEnabledException("User " + login + " was not enabled");
        //}
        
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        for (UserRole authority : userRolesRepo.findByUserId(user.getId())) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getRole());
            grantedAuthorities.add(grantedAuthority);
        }

        return new org.springframework.security.core.userdetails.User(login, user.getPassword(),
                grantedAuthorities);
    }
}

package com.anika.web.service;

import com.anika.core.entity.Query;
import com.anika.core.entity.User;
import com.anika.core.repository.QueryRepository;
import com.anika.core.repository.UserRepository;
import com.anika.web.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QueryRepository queryRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void saveUserQuery(User user, String queryText) {
        // Assume you have obtained the user's input for the query text and keywords
        Query query = new Query(queryText);
        query.setText(queryText);
        query.setTimestamp(LocalDateTime.now());

        // Add the user to the query's users set and add the query to the user's queries set
        query.getUsers().add(user);
        user.getQueries().add(query);

        // Save the changes to the database
        queryRepository.save(query);
        //userRepository.save(user);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findUserByName(String name) {
        return userRepository.findByName(name);
    }

    public Optional<User> findUserByNameOrEmail(String name, String email) {
        return userRepository.findByNameOrEmail(name, email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByName(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                    user.getName(),
                    user.getPassword(),
                    Collections.singleton(Role.USER)
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + username));
    }
}

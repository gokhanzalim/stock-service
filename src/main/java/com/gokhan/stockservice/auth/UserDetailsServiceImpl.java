package com.gokhan.stockservice.auth;

import com.gokhan.stockservice.exception.AuthorizationException;
import com.gokhan.stockservice.exception.ExceptionConstants;
import com.gokhan.stockservice.model.entity.User;
import com.gokhan.stockservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AuthorizationException("User Not Found with username: " + username, ExceptionConstants.USER_NOT_FOUND));

        return UserDetailsImpl.build(user);
    }

}
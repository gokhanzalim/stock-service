package com.gokhan.stockservice.service.impl;

import com.gokhan.stockservice.auth.JwtUtil;
import com.gokhan.stockservice.model.entity.Role;
import com.gokhan.stockservice.model.request.CreateUserRequest;
import com.gokhan.stockservice.model.response.CreateUserResponse;
import com.gokhan.stockservice.repository.RoleRepository;
import com.gokhan.stockservice.repository.UserRepository;
import com.gokhan.stockservice.service.OrderService;
import com.gokhan.stockservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder encoder;
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private OrderService orderService;
    @MockBean
    private Authentication authentication;
    @MockBean
    private SecurityContext securityContext;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        roleRepository = mock(RoleRepository.class);
        encoder = mock(PasswordEncoder.class);
        jwtUtil = mock(JwtUtil.class);
        orderService = mock(OrderService.class);
        authenticationManager = mock(AuthenticationManager.class);
        authentication = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);
        userService = new UserServiceImpl(userRepository,roleRepository,encoder,
                authenticationManager,jwtUtil,orderService);
    }

    @Test
    void testRegisterUser() {
        CreateUserRequest request =
                new CreateUserRequest("gokhan","12345","test@hotmail.com","ADMIN", Collections.EMPTY_SET);

        when(roleRepository.findByName(any())).thenReturn(Optional.of(new Role()));
        CreateUserResponse message = userService.registerUser(request);
        assertEquals(message.getMessage(),"User registered successfully!");


    }

    @Test
    void testAuthenticateUser() {
    }

    @Test
    void testCustomerOrders() {
    }
}
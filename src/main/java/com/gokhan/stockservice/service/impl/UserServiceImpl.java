package com.gokhan.stockservice.service.impl;

import com.gokhan.stockservice.auth.JwtUtil;
import com.gokhan.stockservice.auth.UserDetailsImpl;
import com.gokhan.stockservice.exception.BusinessException;
import com.gokhan.stockservice.exception.ExceptionConstants;
import com.gokhan.stockservice.model.entity.Order;
import com.gokhan.stockservice.model.entity.Role;
import com.gokhan.stockservice.model.entity.User;
import com.gokhan.stockservice.model.enums.RoleType;
import com.gokhan.stockservice.model.enums.UserType;
import com.gokhan.stockservice.model.request.CreateUserRequest;
import com.gokhan.stockservice.model.request.GetCustomerOrderRequest;
import com.gokhan.stockservice.model.request.LoginRequest;
import com.gokhan.stockservice.model.response.CreateUserResponse;
import com.gokhan.stockservice.model.response.CustomerOrderResponse;
import com.gokhan.stockservice.model.response.LoginResponse;
import com.gokhan.stockservice.repository.RoleRepository;
import com.gokhan.stockservice.repository.UserRepository;
import com.gokhan.stockservice.service.OrderService;
import com.gokhan.stockservice.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final OrderService orderService;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder encoder, AuthenticationManager authenticationManager,
                           JwtUtil jwtUtil, OrderService orderService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.orderService = orderService;
    }

    @Override
    public CreateUserResponse registerUser(CreateUserRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BusinessException("Error: Username is already taken!", ExceptionConstants.USER_ALREADY_EXISTS);
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Error: Email is already in use!", ExceptionConstants.EMAIL_ALREADY_EXISTS);
        }
        // Create new user's account
        User user = User.builder()
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .email(request.getEmail())
                .userType(UserType.valueOf(request.getUserType()))
                .build();

        Set<String> requestRoles = request.getRoles();
        Set<Role> roles = this.getRoles(requestRoles);

        user.setUpdateUser("SYSTEM_USER");
        user.setRoles(roles);
        userRepository.save(user);
        return new CreateUserResponse("User registered successfully!");
    }


    private Set<Role> getRoles(Set<String> strRoles) {
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
                    .orElseThrow(() -> new BusinessException("Error: Role is not found.", ExceptionConstants.USER_ROLE_NOT_FOUND));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN)
                                .orElseThrow(() -> new BusinessException("Error: Role is not found.", ExceptionConstants.USER_ROLE_NOT_FOUND));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(RoleType.ROLE_MODERATOR)
                                .orElseThrow(() -> new BusinessException("Error: Role is not found.", ExceptionConstants.USER_ROLE_NOT_FOUND));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
                                .orElseThrow(() -> new BusinessException("Error: Role is not found.", ExceptionConstants.USER_ROLE_NOT_FOUND));
                        roles.add(userRole);
                }
            });
        }
        return roles;
    }

    @Override
    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BusinessException("Incorrect Username or Password", ExceptionConstants.USER_NOT_FOUND, Collections.singletonList(e.getMessage()));
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateToken(loginRequest.getUsername());

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new LoginResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    @Override
    public List<CustomerOrderResponse> getCustomerOrders(GetCustomerOrderRequest request) {
        UserDetailsImpl user = (UserDetailsImpl)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = Optional.ofNullable(request.getUserId()).orElse(user.getId());
        List<Order> orderList = orderService.findByUserIdAndActive(userId, Boolean.TRUE);
        List<CustomerOrderResponse> list = orderList.stream()
                .map(item -> CustomerOrderResponse.builder()
                        .books(item.getBook())
                        .id(item.getId())
                        .quantity(item.getQuantity())
                        .totalAmount(item.getPrice())
                        .userId(item.getUserId()).build())
                .collect(Collectors.toList());

        return list;
    }
}

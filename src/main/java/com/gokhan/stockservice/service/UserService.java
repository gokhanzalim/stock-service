package com.gokhan.stockservice.service;

import com.gokhan.stockservice.model.request.CreateUserRequest;
import com.gokhan.stockservice.model.request.GetCustomerOrderRequest;
import com.gokhan.stockservice.model.request.LoginRequest;
import com.gokhan.stockservice.model.response.CreateUserResponse;
import com.gokhan.stockservice.model.response.CustomerOrderResponse;
import com.gokhan.stockservice.model.response.LoginResponse;

import java.util.List;

public interface UserService {
    CreateUserResponse registerUser(CreateUserRequest createUserRequest);

    LoginResponse authenticateUser(LoginRequest loginRequest);

    List<CustomerOrderResponse> getCustomerOrders(GetCustomerOrderRequest request);
}

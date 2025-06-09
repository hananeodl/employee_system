package com.example.employeeprofile.controller;



import com.example.employeeprofile.model.User;
import com.example.employeeprofile.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserGraphQLController {

    private final UserService userService;

    @QueryMapping
    public List<User> users() {
        return userService.findAllUsers();
    }

    @QueryMapping
    public User user(@Argument Long id) {
        return userService.findById(id);
    }
}

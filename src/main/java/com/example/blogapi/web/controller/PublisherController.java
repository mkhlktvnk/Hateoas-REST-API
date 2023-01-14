package com.example.blogapi.web.controller;

import com.example.blogapi.domain.entity.Publisher;
import com.example.blogapi.service.UserService;
import com.example.blogapi.web.mapper.UserMapper;
import com.example.blogapi.web.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper mapper = UserMapper.INSTANCE;
    private final RepresentationModelAssembler<Publisher, UserModel> userModelAssembler;
    private final PagedResourcesAssembler<Publisher> pagedResourcesAssembler;

    @GetMapping("/api/v0/users")
    public PagedModel<UserModel> getUsers(@PageableDefault Pageable pageable) {
        Page<Publisher> userPage = userService.getUsersByPaging(pageable);
        return pagedResourcesAssembler.toModel(userPage, userModelAssembler);
    }

    @GetMapping("/api/v0/users/{id}")
    public UserModel getUser(@PathVariable Long id) {
        return mapper.mapToModel(userService.getUserById(id));
    }

    @PostMapping("/api/v0/users")
    public UserModel addUser(@RequestBody UserModel user) {
        return mapper.mapToModel(userService.addUser(mapper.mapToEntity(user)));
    }
}

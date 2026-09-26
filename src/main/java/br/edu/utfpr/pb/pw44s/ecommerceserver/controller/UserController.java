package br.edu.utfpr.pb.pw44s.ecommerceserver.controller;

import br.edu.utfpr.pb.pw44s.ecommerceserver.dto.UserDTO;
import br.edu.utfpr.pb.pw44s.ecommerceserver.mapper.UserMapper;
import br.edu.utfpr.pb.pw44s.ecommerceserver.model.User;
import br.edu.utfpr.pb.pw44s.ecommerceserver.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody @Valid UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        userService.save(user);
    }
}
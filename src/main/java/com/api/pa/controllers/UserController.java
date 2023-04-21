package com.api.pa.controllers;

import com.api.pa.dtos.UserDto;
import com.api.pa.models.User;
import com.api.pa.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class UserController {

    final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("new-user")
    public ResponseEntity<Object> saveUser(@RequestBody @Valid UserDto userDto) {
        var userModel = new User();
        BeanUtils.copyProperties(userDto, userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userService.save(userModel));
    }

    @GetMapping("users")
    public ResponseEntity<Page> getAllUsers(@PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll(pageable));
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<Object> getUser(@PathVariable("userId") Integer userId) {
        Optional<User> userModelOptional = userService.findById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
    }

    @DeleteMapping("user/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable("userId") Integer userId) {
        Optional<User> userModelOptional = userService.findById(userId);
        userService.delete(userModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado");
    }

    @PutMapping("user/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable("userId") Integer userId, @RequestBody @Valid UserDto userDto) {
        Optional<User> userModelOptional = userService.findById(userId);
        var userModel = new User();
        BeanUtils.copyProperties(userDto, userModel);
        userModel.setUserId(userModelOptional.get().getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(userService.save(userModel));
    }
}

package com.api.pa.controllers;

import com.api.pa.dtos.UserDto;
import com.api.pa.models.User;
import com.api.pa.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    final UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("new-user")
    public ResponseEntity<Object> saveUser(@RequestBody @Valid UserDto userDto) {
        var userModel = new User();
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        BeanUtils.copyProperties(userDto, userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userService.save(userModel));
    }

    @GetMapping("users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List> getAllUsers(@SortDefault(sort = "userId", direction = Sort.Direction.ASC) Sort sort) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll(sort));
    }

    @GetMapping("user/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getUser(@PathVariable("userId") Integer userId) {
        Optional<User> userModelOptional = userService.findById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
    }

    @DeleteMapping("user/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteUser(@PathVariable("userId") Integer userId) {
        Optional<User> userModelOptional = userService.findById(userId);
        userService.delete(userModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado");
    }

    @PutMapping("user/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> updateUser(@PathVariable("userId") Integer userId, @RequestBody @Valid UserDto userDto) {
        Optional<User> userModelOptional = userService.findById(userId);
        var userModel = new User();
        BeanUtils.copyProperties(userDto, userModel);
        userModel.setUserId(userModelOptional.get().getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(userService.save(userModel));
    }
}

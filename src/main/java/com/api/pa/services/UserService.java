package com.api.pa.services;

import com.api.pa.models.User;
import com.api.pa.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Transactional
    public User save(User user){
        return userRepository.save(user);
    }


    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Optional<User> findById(Integer userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    public void delete(User user) {
        userRepository.delete(user);
    }
}

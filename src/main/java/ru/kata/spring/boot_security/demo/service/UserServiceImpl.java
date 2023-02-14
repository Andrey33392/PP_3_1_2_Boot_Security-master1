package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.reposirory.RoleRepository;
import ru.kata.spring.boot_security.demo.reposirory.UserRepository;

import javax.transaction.Transactional;
import java.rmi.AlreadyBoundException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User passwordCoder(User user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(8);
        user.setPassword(encoder.encode(user.getPassword()));
        return user;
    }

    @Override
    public List<User> findAll() {

        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) {
        return userRepository.getById(id);
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        user = passwordCoder(user);
        user.setRoles(Collections.singleton(new Role(1, "ROLE_USER")));

        userRepository.saveUser(user);

    }

    @Transactional
    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteUserById(id);

    }

    @Transactional
    @Override
    public void updateUser(User user) {

        user = passwordCoder(user);

        userRepository.updateUser(user);

    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }




}

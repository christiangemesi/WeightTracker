package com.example.demo.Service;

import com.example.demo.model.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void addUser(String username, String password, Set<String> authorities) {
        this.userRepository.save(new User(username, this.passwordEncoder.encode(password), authorities));
    }

    public boolean usernameAlreadyExists(String username) {
        return this.userRepository.findByUsername(username).isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found."));
    }

    public Optional<User> findContact(int id) {
        return userRepository.findById((long) id);
    }

    public User update(User user) {
        return userRepository.save(user); // 'save' might return new object
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public void deleteId(Long id) {
        userRepository.deleteUserById(id);
    }

}
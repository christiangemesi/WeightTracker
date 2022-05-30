package ch.fhnw.webeng.weighttracker.services;

import ch.fhnw.webeng.weighttracker.models.User;
import ch.fhnw.webeng.weighttracker.repositories.UserRepository;
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

    public User create(String username, String password, Set<String> authorities) {
        return this.userRepository.save(new User(username, this.passwordEncoder.encode(password), authorities));
    }

    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> (
            new UsernameNotFoundException("No such user found: " + username)
        ));
    }

    public Optional<User> findContact(int id) {
        return userRepository.findById((long) id);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public User updatePassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }


    public void delete(User user) {
        userRepository.delete(user);
    }
}
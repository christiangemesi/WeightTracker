package ch.fhnw.webeng.weighttracker.services;

import ch.fhnw.webeng.weighttracker.models.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    public void setCurrentUser(User user) {
        Authentication auth = new UsernamePasswordAuthenticationToken(
            user,
            user.getPassword(),
            user.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    public void clearCurrentUser() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    public Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return Optional.empty();
        }
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof User)) {
            return Optional.empty();
        }
        User currentUser = (User) authentication.getPrincipal();
        return Optional.of(currentUser);
    }

    public User requireCurrentUser() {
        return getCurrentUser().orElseThrow(() -> (
            new IllegalStateException("not authenticated")
        ));
    }
}

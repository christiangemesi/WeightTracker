package com.example.demo.controllers;

import com.example.demo.services.UserService;
import com.example.demo.models.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {


    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/user/edit")
    public String edit(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        model.addAttribute("user", currentUser);
        return "pages/user/edit";
    }

    @PostMapping("/user/edit")
    public String update(
        @RequestParam String username,
        @RequestParam String password
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        currentUser.setUsername(username);
        currentUser.setPassword(passwordEncoder.encode(password));
        currentUser = userService.update(currentUser);

        Authentication auth = new UsernamePasswordAuthenticationToken(
            currentUser,
            currentUser.getPassword(),
            currentUser.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        return "redirect:/";
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable int id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User customUser = (User) authentication.getPrincipal();
        long userId = customUser.getId();

        User user = userService.findContact(id).orElseThrow();

        if (user.getId() == userId) {
            userService.delete(user);
            SecurityContextHolder.getContext().setAuthentication(null);

            return "redirect:/pages/login";
        }
        return "pages/error";
    }
}

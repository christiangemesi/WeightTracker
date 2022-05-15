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

    @GetMapping("/{id}/edit")
    public String showUser(@PathVariable int id,
                           Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User customUser = (User) authentication.getPrincipal();
        long userId = customUser.getId();
        User user = userService.findContact(id).orElseThrow();
        if (user.getId() == userId) {
            model.addAttribute("user", user);
            return "pages/edit";
        }
        return "pages/error";
    }

    @PostMapping("/{id}/edit")
    public String editContact(@PathVariable int id,
                              @RequestParam String username,
                              @RequestParam String password) {
        var contact = userService.findContact(id).orElseThrow();
        contact.setUsername(username);
        contact.setPassword(passwordEncoder.encode(password));
        System.out.println(username);
        System.out.println(password);

        Authentication auth = new UsernamePasswordAuthenticationToken(contact,contact.getPassword(),contact.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        userService.update(contact);
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

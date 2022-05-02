package com.example.demo.Controllers;

import com.example.demo.Service.UserService;
import com.example.demo.model.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@Controller
public class LoginController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginController(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login(Authentication authentication, @RequestParam("error") Optional<String> error, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        } else {
            model.addAttribute("hasLoginError", error.isPresent());

            return "login";
        }
    }

    @RequestMapping(path = "/signup", method = RequestMethod.GET)
    public String signup() {
        return "signup";
    }

    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    public String signup(User user, BindingResult bindingResult, Model model) {
        if (this.userService.usernameAlreadyExists(user.getUsername())) {
            bindingResult.addError(new FieldError("user", "username", "Username already exists"));
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);

            return "signup";
        } else {
            this.userService.addUser(user.getUsername(), user.getPassword(), Set.of("ROLE_USER"));

            return "redirect:/login";
        }
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
            return "edit";
        }
        return "error";
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

            return "redirect:/login";
        }
        return "error";
    }
}

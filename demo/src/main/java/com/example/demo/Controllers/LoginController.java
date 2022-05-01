package com.example.demo.Controllers;

import com.example.demo.Service.UserService;
import com.example.demo.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
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
    public String signup( User user, BindingResult bindingResult, Model model) {
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

    @GetMapping("/{id}")
    public String editUser(@PathVariable int id, Model model) {
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        User customUser = (User)authentication.getPrincipal();
        long userId = customUser.getId();
        User user = userService.findContact(id).orElseThrow();
        if(user.getId() == userId){
            model.addAttribute("user", user);
            return "edit";
        }
        return "error";
    }


}

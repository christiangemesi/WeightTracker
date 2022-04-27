package com.example.demo.Controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login(Authentication authentication, @RequestParam("error") Optional<String> error, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        } else {
            model.addAttribute("hasLoginError", error.isPresent());

            return "login";
        }
    }
}

package ch.fhnw.webeng.weighttracker.controllers;

import ch.fhnw.webeng.weighttracker.services.AccountService;
import ch.fhnw.webeng.weighttracker.services.UserService;
import ch.fhnw.webeng.weighttracker.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.Set;

@Controller
public class LoginController {

    private final UserService userService;
    private final AccountService accountService;

    public LoginController(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login(
        Authentication authentication,
        Model model
    ) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }
        return "pages/login";
    }

    @RequestMapping(path = "/signup", method = RequestMethod.GET)
    public String signUp() {
        return "pages/signup";
    }

    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    public String signup(
        User user,
        RedirectAttributes redirectAttributes
    ) {
        redirectAttributes.addFlashAttribute("username", user.getUsername());
        if (user.getUsername().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Username must not be empty.");
            return "redirect:/signup";
        }
        if (user.getPassword().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Password must not be empty.");
            return "redirect:/signup";
        }
        if (this.userService.findByUsername(user.getUsername()).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Username already exists.");
            return "redirect:/signup";
        }

        user = this.userService.create(user.getUsername(), user.getPassword(), Set.of("ROLE_USER"));
        accountService.setCurrentUser(user);
        return "redirect:/";
    }
}

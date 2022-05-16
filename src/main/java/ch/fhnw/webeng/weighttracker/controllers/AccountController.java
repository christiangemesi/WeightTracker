package ch.fhnw.webeng.weighttracker.controllers;

import ch.fhnw.webeng.weighttracker.services.AccountService;
import ch.fhnw.webeng.weighttracker.services.UserService;
import ch.fhnw.webeng.weighttracker.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("account")
public class AccountController {
    private final UserService userService;
    private final AccountService accountService;

    public AccountController(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @GetMapping
    public String show() {
        return "pages/account";
    }

    @PutMapping("/username")
    public String updateUsername(
        @RequestParam String username,
        RedirectAttributes redirectAttributes
    ) {
        User currentUser = accountService.requireCurrentUser();
        currentUser.setUsername(username);
        currentUser = userService.update(currentUser);
        accountService.setCurrentUser(currentUser);
        redirectAttributes.addFlashAttribute("success", "Username has been changed.");
        return "redirect:/account";
    }

    @PutMapping("/password")
    public String updatePassword(
        @RequestParam String password,
        @RequestParam("password-confirmation") String passwordConfirmation,
        RedirectAttributes redirectAttributes
    ) {
        if (!password.equals(passwordConfirmation)) {
            redirectAttributes.addFlashAttribute("error", "Passwords did not match!");
            return "redirect:/account?tab=1";
        }
        User currentUser = accountService.requireCurrentUser();
        currentUser = userService.updatePassword(currentUser, password);
        accountService.setCurrentUser(currentUser);
        redirectAttributes.addFlashAttribute("success", "Password has been changed.");
        return "redirect:/account?tab=1";
    }

    @DeleteMapping
    public String delete(RedirectAttributes redirectAttributes) {
        User currentUser = accountService.requireCurrentUser();
        userService.delete(currentUser);
        accountService.clearCurrentUser();
        redirectAttributes.addFlashAttribute("success", "Your account has been deleted.");
        return "redirect:/login";
    }

    @ModelAttribute
    public User user() {
        return accountService.requireCurrentUser();
    }
}

package com.example.demo.controllers;

import com.example.demo.services.AccountService;
import com.example.demo.services.UserService;
import com.example.demo.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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
    public String updateUsername(@RequestParam String username, RedirectAttributes redirectAttributes) {
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
        @RequestParam String passwordConfirmation
    ) {
        if (!password.equals(passwordConfirmation)) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "passwords must match");
        }
        User currentUser = accountService.requireCurrentUser();
        currentUser = userService.updatePassword(currentUser, password);
        accountService.setCurrentUser(currentUser);
        return "redirect:/account";
    }

    @DeleteMapping
    public String delete() {
        System.out.println("yes its me");

        User currentUser = accountService.requireCurrentUser();
        userService.delete(currentUser);
        accountService.clearCurrentUser();
        return "redirect:/login";
    }

    @ModelAttribute
    public User user() {
        return accountService.requireCurrentUser();
    }
}

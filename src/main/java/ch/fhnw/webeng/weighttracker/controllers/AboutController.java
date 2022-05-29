package ch.fhnw.webeng.weighttracker.controllers;

import ch.fhnw.webeng.weighttracker.services.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AboutController {
    private final AccountService accountService;

    public AboutController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping("/about")
    public String about(Model model) {
        model.addAttribute("hasUser", accountService.getCurrentUser().isPresent());
        return "pages/about.html";
    }
}

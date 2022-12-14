package ch.fhnw.webeng.weighttracker.controllers;

import ch.fhnw.webeng.weighttracker.models.User;
import ch.fhnw.webeng.weighttracker.models.WeightEntry;
import ch.fhnw.webeng.weighttracker.services.AccountService;
import ch.fhnw.webeng.weighttracker.services.WeightEntryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class HomeController {
    private final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final WeightEntryService weightEntryService;
    private final AccountService accountService;

    public HomeController(WeightEntryService weightEntryService, AccountService accountService) {
        this.weightEntryService = weightEntryService;
        this.accountService = accountService;
    }

    @RequestMapping("/")
    public String index(Model model) {
        User currentUser = accountService.requireCurrentUser();
        List<WeightEntry> weightEntries = weightEntryService.findAllByUserId(currentUser.getId());
        model.addAttribute("weightEntries", weightEntries);
        model.addAttribute("user", currentUser);

        if (!weightEntries.isEmpty()) {
            WeightEntry firstEntry = weightEntries.get(0);
            model.addAttribute("firstEntryDate", firstEntry.getDate().format(DATE_FORMATTER));
            if (weightEntries.size() > 1) {
                WeightEntry lastEntry = weightEntries.get(weightEntries.size() - 1);
                model.addAttribute("lastEntryDate", lastEntry.getDate().format(DATE_FORMATTER));
            }
        }

        return "pages/home";
    }
}
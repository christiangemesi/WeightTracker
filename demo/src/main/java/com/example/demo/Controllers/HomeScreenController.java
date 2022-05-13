package com.example.demo.Controllers;

import com.example.demo.Service.WeightEntityService;
import com.example.demo.model.User;
import com.example.demo.model.WeightEntry;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeScreenController {

    private final WeightEntityService weightEntityService;

    public HomeScreenController(WeightEntityService weightEntityService) {
        this.weightEntityService = weightEntityService;
    }

    @RequestMapping("/")
    public String welcome(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User customUser = (User) authentication.getPrincipal();

        List<WeightEntry> weightEntries = weightEntityService.getAllWeights(customUser.getId());
        model.addAttribute("weightEntries", weightEntries);
        model.addAttribute("user", customUser);

        return "home.html";
    }
}
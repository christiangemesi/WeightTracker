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

    @RequestMapping("/")
    public String  welcome(Model model) {
        return "home.html";
    }

    @RequestMapping("/about")
    public String  about() {
        return "about.html";
    }

}

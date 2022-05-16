package ch.fhnw.webeng.weighttracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AboutController {

    @RequestMapping("/about")
    public String about() {
        return "pages/about.html";
    }
}

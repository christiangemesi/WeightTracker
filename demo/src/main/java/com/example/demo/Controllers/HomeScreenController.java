package com.example.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeScreenController {

    @RequestMapping("/")
    public String  welcome() {
        return "home.html";
    }

    @RequestMapping("/addweight")
    public String  addWeight() {
        return "addWeight.html";
    }

    @RequestMapping("/signup")
    public String  signUp() {
        return "signUp.html";
    }

    @RequestMapping("/about")
    public String  about() {
        return "about.html";
    }

}

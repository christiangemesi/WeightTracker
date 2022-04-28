package com.example.demo.Controllers;

import com.example.demo.model.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

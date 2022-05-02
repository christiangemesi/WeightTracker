package com.example.demo.Controllers;

import com.example.demo.Service.UserService;
import com.example.demo.model.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @RequestMapping("/users")
    public ModelAndView getUsers(Model model) {

        model.addAttribute("users",userRepository.findAll());

        return new ModelAndView("list");
    }

}

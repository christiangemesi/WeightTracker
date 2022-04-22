package com.example.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AddWeightController {

    @RequestMapping("/add-Weight")
    public String  login() {
        return "AddWeight.html";
    }

}

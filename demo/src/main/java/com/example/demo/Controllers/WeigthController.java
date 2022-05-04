package com.example.demo.Controllers;

import com.example.demo.Service.WeightEntityService;
import com.example.demo.model.User;
import com.example.demo.model.WeightEntry;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class WeigthController {

    private final WeightEntityService weightEntityService;

    public WeigthController(WeightEntityService weightEntityService) {
        this.weightEntityService = weightEntityService;
    }

    @RequestMapping(path = "/addweight", method = RequestMethod.POST)
    public String addWeight(WeightEntry weightEntry, BindingResult bindingResult, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User customUser = (User) authentication.getPrincipal();

        weightEntityService.addWeightEntity(weightEntry.getWeight(),weightEntry.getDate(), customUser);

            return "redirect:/addweight";

    }

}




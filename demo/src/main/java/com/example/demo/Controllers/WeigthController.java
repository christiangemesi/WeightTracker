package com.example.demo.Controllers;

import com.example.demo.Service.ImageFileService;
import com.example.demo.Service.WeightEntityService;
import com.example.demo.model.User;
import com.example.demo.model.WeightEntry;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
public class WeigthController {

    private final WeightEntityService weightEntityService;
    private final ImageFileService imageFileService;

    public WeigthController(WeightEntityService weightEntityService, ImageFileService imageFileService) {
        this.weightEntityService = weightEntityService;
        this.imageFileService = imageFileService;
    }

    @RequestMapping(path = "/addweight", method = RequestMethod.POST)
    public String addWeight(WeightEntry weightEntry, BindingResult bindingResult, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User customUser = (User) authentication.getPrincipal();

        weightEntry = weightEntityService.addWeightEntity(weightEntry.getWeight(), weightEntry.getDate(), customUser);
        model.addAttribute("weightEntry", weightEntry);

        return "redirect:/addweight";
    }

    @RequestMapping(path = "/weight-entries/{weightEntryId}/images", method = RequestMethod.POST)
    public String addImage(
            @RequestParam("front") MultipartFile front,
            @RequestParam("back") MultipartFile back,
            @RequestParam("side") MultipartFile side,
            @PathVariable("weightEntryId") long weightEntryId, Model model) {
        try {
            imageFileService.save(front.getBytes(), front.getName(), weightEntryId);
            imageFileService.save(back.getBytes(), back.getName(), weightEntryId);
            imageFileService.save(side.getBytes(), side.getName(), weightEntryId);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/addweight";
    }
}




package com.example.demo.Controllers;

import com.example.demo.Service.ImageFileService;
import com.example.demo.Service.WeightEntityService;
import com.example.demo.model.Image;
import com.example.demo.model.User;
import com.example.demo.model.WeightEntry;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;


@Controller
public class WeigthController {

    private final WeightEntityService weightEntityService;
    private final ImageFileService imageFileService;

    public WeigthController(WeightEntityService weightEntityService, ImageFileService imageFileService) {
        this.weightEntityService = weightEntityService;
        this.imageFileService = imageFileService;
    }

    @RequestMapping(path = "/addweight", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String addWeight(WeightEntry weightEntry,
                            @RequestParam("front") MultipartFile front,
                            @RequestParam("back") MultipartFile back,
                            @RequestParam("side") MultipartFile side,
                            Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User customUser = (User) authentication.getPrincipal();

        weightEntry = weightEntityService.addWeightEntity(weightEntry.getWeight(), weightEntry.getDate(), customUser);
        model.addAttribute("weightEntry", weightEntry);

        //Im sure this can be done better
        try {
            if (front.getBytes().length != 0) {
                imageFileService.save(front.getBytes(), front.getName(), weightEntry.getId());
            }
            if(back.getBytes().length != 0){
                imageFileService.save(back.getBytes(), back.getName(), weightEntry.getId());
            }
            if(side.getBytes().length != 0){
                imageFileService.save(side.getBytes(), side.getName(), weightEntry.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "redirect:/addweight";
    }
}




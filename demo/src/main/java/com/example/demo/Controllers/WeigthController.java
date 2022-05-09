package com.example.demo.Controllers;

import com.example.demo.Service.ImageFileService;
import com.example.demo.Service.WeightEntityService;
import com.example.demo.model.User;
import com.example.demo.model.WeightEntry;
import com.example.demo.repositories.WeightEntryRepository;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
public class WeigthController {

    private final WeightEntityService weightEntityService;
    private final ImageFileService imageFileService;
    private final WeightEntryRepository weightEntryRepository;


    public WeigthController(WeightEntityService weightEntityService, ImageFileService imageFileService, WeightEntryRepository weightEntryRepository) {
        this.weightEntityService = weightEntityService;
        this.imageFileService = imageFileService;
        this.weightEntryRepository = weightEntryRepository;
    }

    @RequestMapping(path = "/addweight", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String addWeight(WeightEntry weightEntry,
                            @RequestParam("front") MultipartFile front,
                            @RequestParam("back") MultipartFile back,
                            @RequestParam("side") MultipartFile side,
                            Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User customUser = (User) authentication.getPrincipal();


        WeightEntry duplicate = weightEntityService.isDuplicateWeightEntryPresent(weightEntry.getDate(),customUser);

        if(duplicate != null){
            weightEntityService.removeWeightEntryById(duplicate.getId());
        }

        weightEntityService.addWeightEntity(weightEntry.getWeight(), weightEntry.getDate(), customUser);

        model.addAttribute("WeightEntries", weightEntry);

        //TODO Im sure this can be done better
        try {
            if (front.getBytes().length != 0) {
                imageFileService.save(front.getBytes(), front.getName(), weightEntry.getId());
            }
            if (back.getBytes().length != 0) {
                imageFileService.save(back.getBytes(), back.getName(), weightEntry.getId());
            }
            if (side.getBytes().length != 0) {
                imageFileService.save(side.getBytes(), side.getName(), weightEntry.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/addweight";
    }
}




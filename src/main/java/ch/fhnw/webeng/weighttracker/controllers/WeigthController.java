package ch.fhnw.webeng.weighttracker.controllers;

import ch.fhnw.webeng.weighttracker.services.ImageFileService;
import ch.fhnw.webeng.weighttracker.services.WeightEntityService;
import ch.fhnw.webeng.weighttracker.models.Image;
import ch.fhnw.webeng.weighttracker.models.User;
import ch.fhnw.webeng.weighttracker.models.WeightEntry;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @RequestMapping("/addweight")
    public String  addWeight() {
        return "pages/addWeight.html";
    }

    @RequestMapping(path = "/addweight", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String addWeight(WeightEntry weightEntry,
                            @RequestParam("front") MultipartFile front,
                            @RequestParam("back") MultipartFile back,
                            @RequestParam("side") MultipartFile side,
                            Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User customUser = (User) authentication.getPrincipal();

        WeightEntry duplicate = weightEntityService.isDuplicateWeightEntryPresent(weightEntry.getDate(), customUser);

        if (duplicate != null) {
            weightEntityService.removeWeightEntryById(duplicate.getId());
        }

        weightEntry = weightEntityService.addWeightEntity(weightEntry.getWeight(), weightEntry.getDate(), customUser);

        try {
            if (front.getBytes().length != 0) {
                Image image = new Image(front.getName(),front.getBytes(),weightEntry);

                imageFileService.save(front.getBytes(), front.getName(), weightEntry.getId());
                model.addAttribute("front", image);
            }
            if (back.getBytes().length != 0) {
                Image image = new Image(back.getName(),back.getBytes(),weightEntry);
                model.addAttribute("back", image);
                imageFileService.save(back.getBytes(), back.getName(), weightEntry.getId());
            }
            if (side.getBytes().length != 0) {
                Image image = new Image(side.getName(),side.getBytes(),weightEntry);
                model.addAttribute("side", image);
                imageFileService.save(side.getBytes(), side.getName(), weightEntry.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        model.addAttribute("WeightEntries", weightEntry);

        return "redirect:/";
    }

    @GetMapping("/editweight/{id}")
    public String editWeight(@PathVariable long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User customUser = (User) authentication.getPrincipal();

        WeightEntry weightEntry = weightEntityService.getWeightEntryById(id);
        if (!weightEntry.getUser().getId().equals(customUser.getId())) {
            return "pages/error";
        }

        model.addAttribute("weightEntry", weightEntry);
        model.addAttribute("front", weightEntry.getImageList().stream()
                .filter(image -> image.getName().equals("front"))
                .findFirst().orElse(null));

        model.addAttribute("back", weightEntry.getImageList().stream()
                .filter(image -> image.getName().equals("back"))
                .findFirst().orElse(null));

        model.addAttribute("side", weightEntry.getImageList().stream()
                .filter(image -> image.getName().equals("side"))
                .findFirst().orElse(null));

        return "pages/editWeight.html";
    }

    @PostMapping("/editweight/{id}")
    public String editWeight(
            WeightEntry weightEntry,
            @RequestParam("front") MultipartFile front,
            @RequestParam("back") MultipartFile back,
            @RequestParam("side") MultipartFile side,
            @PathVariable long id, Model model
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        weightEntry = weightEntityService.updateWeightEntry(id, weightEntry.getWeight(), weightEntry.getDate());

        model.addAttribute("WeightEntries", weightEntry);

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

        return "redirect:/";
    }

    @GetMapping("/weightentries/{weightEntryId}/images/{imageId}")
    public ResponseEntity<byte[]> getImageById(@PathVariable long weightEntryId, @PathVariable long imageId) {
        WeightEntry weightEntry = weightEntityService.getWeightEntryById(weightEntryId);
        Image image = weightEntry.getImageList().stream()
                .filter(img -> img.getId().equals(imageId))
                .findFirst().orElseThrow();

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(image.getMimeType()))
                .body(image.getFile());
    }
}
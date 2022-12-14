package ch.fhnw.webeng.weighttracker.controllers;

import ch.fhnw.webeng.weighttracker.models.Image;
import ch.fhnw.webeng.weighttracker.models.User;
import ch.fhnw.webeng.weighttracker.models.WeightEntry;
import ch.fhnw.webeng.weighttracker.services.AccountService;
import ch.fhnw.webeng.weighttracker.services.ImageService;
import ch.fhnw.webeng.weighttracker.services.WeightEntryService;
import org.apache.tika.Tika;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;


@Controller
@RequestMapping("weights")
public class WeightController {
    private final WeightEntryService weightEntryService;
    private final AccountService accountService;
    private final ImageService imageService;

    public WeightController(
        WeightEntryService weightEntryService,
        AccountService accountService,
        ImageService imageService
    ) {
        this.weightEntryService = weightEntryService;
        this.accountService = accountService;
        this.imageService = imageService;
    }

    @GetMapping("add")
    public String showAdd(Model model) {
        model.addAttribute("currentDate", LocalDate.now().toString());
        return "/pages/weights/form";
    }

    @GetMapping("{id}/edit")
    public String showEdit(@PathVariable Long id, Model model) {
        WeightEntry entry = loadWeightEntry(id);
        model.addAttribute("entry", entry);
        return "/pages/weights/form";
    }

    @PostMapping
    public String save(
        @RequestParam(required = false) Long id,
        @RequestParam double weight,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @RequestParam MultipartFile[] images
    ) {
        User currentUser = accountService.requireCurrentUser();
        WeightEntry entry = id == null
            ? new WeightEntry(weight, date, currentUser)
            : loadWeightEntry(id);
        entry.setWeight(weight);
        entry.setDate(date);
        entry.getImages().addAll(Arrays.stream(images)
            .map((file) -> {
                try {
                    byte[] bytes = file.getBytes();
                    return new Image(file.getName(), bytes, entry, null);
                } catch (IOException e) {
                    throw new IllegalStateException("failed to read uploaded file", e);
                }
            })
            .filter((image) -> image.getFile().length > 0)
            .peek((image) -> {
                String mimeType = new Tika().detect(image.getFile());
                if (!mimeType.startsWith("image/")) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "file must be an image, was " + mimeType);
                }
                image.setMimeType(mimeType);
            })
            .toList()
        );
        weightEntryService.save(entry);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        System.out.println("DELETE" + id);
        WeightEntry entry = loadWeightEntry(id);
        weightEntryService.delete(entry.getId());
        return "redirect:/";
    }

    @GetMapping("/{id}/images/{imageId}")
    public ResponseEntity<byte[]> showImage(@PathVariable Long id, @PathVariable Long imageId) {
        WeightEntry weightEntry = loadWeightEntry(id);
        Image image = weightEntry.getImages().stream()
            .filter(img -> img.getId().equals(imageId))
            .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok()
            .contentType(MediaType.valueOf(image.getMimeType()))
            .body(image.getFile());
    }

    @DeleteMapping("/{id}/images/{imageId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImage(@PathVariable Long id, @PathVariable Long imageId) {
        WeightEntry weightEntry = loadWeightEntry(id);
        Image image = weightEntry.getImages().stream()
            .filter(img -> img.getId().equals(imageId))
            .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        imageService.delete(image);
    }

    private WeightEntry loadWeightEntry(Long id) {
        User currentUser = accountService.requireCurrentUser();
        WeightEntry entry = weightEntryService.find(id).orElseThrow(() -> (
            new ResponseStatusException(HttpStatus.NOT_FOUND)
        ));
        if (!Objects.equals(entry.getUser().getId(), currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "user is not allowed to modify this entry");
        }
        return entry;
    }
}
package ch.fhnw.webeng.weighttracker.controllers;

import ch.fhnw.webeng.weighttracker.models.Image;
import ch.fhnw.webeng.weighttracker.models.User;
import ch.fhnw.webeng.weighttracker.models.WeightEntry;
import ch.fhnw.webeng.weighttracker.services.AccountService;
import ch.fhnw.webeng.weighttracker.services.WeightEntityService;
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
    private final WeightEntityService weightEntityService;
    private final AccountService accountService;

    public WeightController(
        WeightEntityService weightEntityService,
        AccountService accountService
    ) {
        this.weightEntityService = weightEntityService;
        this.accountService = accountService;
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
        entry.getImageList().addAll(Arrays.stream(images)
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
        weightEntityService.save(entry);
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

    private WeightEntry loadWeightEntry(Long id) {
        User currentUser = accountService.requireCurrentUser();
        WeightEntry entry = weightEntityService.getWeightEntryById(id);
        if (entry == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (!Objects.equals(entry.getUser().getId(), currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "user is not allowed to modify this entry");
        }
        return entry;
    }
}
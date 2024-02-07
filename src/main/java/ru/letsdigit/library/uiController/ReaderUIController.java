package ru.letsdigit.library.uiController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.letsdigit.library.entity.Reader;
import ru.letsdigit.library.service.LibraryService;

import java.util.UUID;

@Controller
@RequestMapping(value = "/ui/reader")
public class ReaderUIController {

    private final LibraryService<Reader> libraryService;

    @Autowired
    public ReaderUIController(LibraryService<Reader> libraryService) {
        this.libraryService = libraryService;
    }

    /*
     * Предоставление детальной информации по читателю
     */
    @GetMapping
    public String findById(@RequestParam(name = "id") UUID id, Model model) {
        libraryService.findById(id)
                .map(value -> model.addAttribute("details", value))
                .orElseThrow(() -> new RuntimeException("Not Found"));
        return "reader-details";
    }

    /*
     * !! Также изменен на /ui/reader/all
     */
    @GetMapping(value = "/all")
    public String findAll(Model model) {
        model.addAttribute("readers", libraryService.findAll());
        return "readers";
    }
}

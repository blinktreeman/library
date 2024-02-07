package ru.letsdigit.library.uiController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.letsdigit.library.service.IssueService;

@Controller
@RequestMapping(value = "/ui/issue")
public class IssueUIController {

    private final IssueService service;

    @Autowired
    public IssueUIController(IssueService service) {
        this.service = service;
    }

    /*
     * 1.3 /ui/issues - на странице отображается таблица, в которой есть столбцы
     * (книга, читатель, когда взял, когда вернул (если не вернул - пустая ячейка)).
     * !! Изменено на /ui/issue/all
     */
    @GetMapping(value = "/all")
    public String findAll(Model model) {
        model.addAttribute("issues", service.findAll());
        return "issues";
    }
}

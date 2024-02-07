package ru.letsdigit.library.uiController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.letsdigit.library.entity.Book;
import ru.letsdigit.library.service.LibraryService;

/*
 * 1. В предыдущий проект (где была библиотека с книгами, выдачами и читателями) добавить следующие рерурсы,
 * которые возвращают готовые HTML-страницы (т.е. это просто Controller'ы):
 * 1.1 /ui/books - на странице должен отобразиться список всех доступных книг в системе
 * 1.2 /ui/reader - аналогично 1.1
 * 1.3 /ui/issues - на странице отображается таблица, в которой есть столбцы
 * (книга, читатель, когда взял, когда вернул (если не вернул - пустая ячейка)).
 * 1.4* /ui/reader/{id} - страница, где написано имя читателя с идентификатором id и перечислены книги,
 * которые на руках у этого читателя
 */

@Controller
@RequestMapping(value = "/ui/book")
public class BookUIController {

    private final LibraryService<Book> libraryService;

    @Autowired
    public BookUIController(LibraryService<Book> libraryService) {
        this.libraryService = libraryService;
    }

    /*
     * 1.1 /ui/books - на странице должен отобразиться список всех доступных книг в системе
     * !! изменен на /ui/book/all
    */
    @GetMapping(value = "/all")
    public String findAll(Model model) {
        model.addAttribute("books", libraryService.findAll());
        return "books";
    }
}

package ru.letsdigit.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.letsdigit.library.entity.Book;
import ru.letsdigit.library.service.LibraryService;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/book")
public class BookController {

    private final LibraryService<Book> libraryService;

    @Autowired
    public BookController(LibraryService<Book> libraryService) {
        this.libraryService = libraryService;
    }

    /* POST /book - создать книгу */
    @PostMapping
    public ResponseEntity<Book> save(@RequestBody Book book) {
        return new ResponseEntity<>(libraryService.save(book), HttpStatus.CREATED);
    }

    /* 1.1 Реализовать контроллер по управлению книгами с ручками:
     * GET /book/{id} - получить описание книги
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Book> findById(@PathVariable UUID id) {
        return libraryService
                .findById(id)
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<Iterable<Book>> findAll() {
        return new ResponseEntity<>(libraryService.findAll(), HttpStatus.OK);
    }

    /* DELETE /book/{id} - удалить книгу, */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable UUID id) {
        libraryService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

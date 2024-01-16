package ru.letsdigit.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.letsdigit.library.entity.Book;
import ru.letsdigit.library.service.IService;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping(value = "/book")
public class BookController {

    private final IService<Book> service;

    @Autowired
    public BookController(IService<Book> service) {
        this.service = service;
    }

    /* POST /book - создать книгу */
    @PostMapping
    public ResponseEntity<Book> save(@RequestBody Book book) {
        return new ResponseEntity<>(service.save(book), HttpStatus.CREATED);
    }

    /* 1.1 Реализовать контроллер по управлению книгами с ручками:
     * GET /book/{id} - получить описание книги
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Book> findById(@PathVariable UUID id) {
        return service
                .findById(id)
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<Iterable<Book>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    /* DELETE /book/{id} - удалить книгу, */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable UUID id) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

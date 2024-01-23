package ru.letsdigit.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.letsdigit.library.entity.Issue;
import ru.letsdigit.library.entity.Reader;
import ru.letsdigit.library.service.IService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/api/v1/reader")
public class ReaderController {

    private final IService<Reader> service;

    @Autowired
    public ReaderController(IService<Reader> service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Reader> save(@RequestBody Reader reader) {
        return new ResponseEntity<>(service.save(reader), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Reader> findById(@PathVariable UUID id) {
        return service
                .findById(id)
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /* 2.2 В сервис читателя добавить ручку GET /reader/{id}/issue
     * - вернуть список всех выдачей для данного читателя
     */
    @GetMapping(value = "/{id}/issue")
    public ResponseEntity<List<Issue>> notReturnedBooksByReaderId(@PathVariable UUID id) {
        Optional<List<Issue>> issues = service.findById(id).map(value -> value
                .getIssues()
                .stream()
                .filter(issue -> issue.getReturnedAt() == null) // Только не сданные книги
                .collect(Collectors.toList()));
        return issues
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<Iterable<Reader>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable UUID id) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

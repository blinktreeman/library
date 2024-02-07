package ru.letsdigit.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.letsdigit.library.entity.Issue;
import ru.letsdigit.library.service.IssueService;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/issue")
public class IssueController {

    private final IssueService service;

    @Autowired
    public IssueController(IssueService service) {
        this.service = service;
    }

    /* 2.1 В сервис IssueService добавить проверку, что у пользователя на руках нет книг.
     * Если есть - не выдавать книгу (статус ответа - 409 Conflict)
     */
    @PostMapping
    public ResponseEntity<Issue> save(@RequestBody Issue issue) {
        return service
                .save(issue)
                .map(value -> new ResponseEntity<>(value, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Issue> findById(@PathVariable UUID id) {
        return service
                .findById(id)
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<Iterable<Issue>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    /* 3.2* К ресурс POST /issue добавить запрос PUT /issue/{issueId}, который закрывает факт выдачи.
     * (т.е. проставляет returned_at в Issue).
     * !! добавил /turned
     */
    @PutMapping(value = "/{id}/turned")
    public ResponseEntity<Issue> markBookTurnedIn(@PathVariable UUID id) {
        Optional<Issue> issue = service.findById(id);
        if (issue.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        issue.get().setReturnedAt(new Date());
        return new ResponseEntity<>(service.update(issue.get()), HttpStatus.OK);
    }
}

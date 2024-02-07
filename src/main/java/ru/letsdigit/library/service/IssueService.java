package ru.letsdigit.library.service;

import ru.letsdigit.library.entity.Issue;

import java.util.Optional;
import java.util.UUID;

public interface IssueService {
    Optional<Issue> save(Issue issue);
    Optional<Issue> findById(UUID uuid);
    Iterable<Issue> findAll();
    Issue update(Issue issue);
    void deleteById(UUID uuid);
}

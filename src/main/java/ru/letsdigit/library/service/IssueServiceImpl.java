package ru.letsdigit.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.letsdigit.library.entity.Issue;
import ru.letsdigit.library.repository.IIssueRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class IssueServiceImpl implements IIssueService {

    private final IIssueRepository repository;

    /* Должно задаваться в конфигурации (параметр application.issue.max-allowed-books).
     * Если параметр не задан - то использовать значение 1
     */
    @Value("${application.issue.max-allowed-books}")
    private int MAX_ALLOWED_BOOKS = 1;

    @Autowired
    public IssueServiceImpl(IIssueRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Issue> save(Issue issue) {
        /* 3.3** Пункт 2.1 расширить параметром, сколько книг может быть на руках у пользователя. */
        List<Issue> readerIssues = (List<Issue>) repository.findAllByReader(issue.getReader());
        if (readerIssues.size() >= MAX_ALLOWED_BOOKS) {
            return Optional.empty();
        }
        issue.getReader().getIssues().add(issue);
        return Optional.of(repository.save(issue));
    }

    @Override
    public Optional<Issue> findById(UUID uuid) {
        return repository.findById(uuid);
    }

    @Override
    public Iterable<Issue> findAll() {
        return repository.findAll();
    }

    public Issue update(Issue issue) {
        return repository.findById(issue.getUuid())
                .map(value -> repository.save(issue))
                .orElseThrow(() -> new RuntimeException("Issue not found"));
    }

    @Override
    public void deleteById(UUID uuid) {
        Issue issue = repository
                .findById(uuid)
                .orElseThrow(() -> new RuntimeException("Issue not found"));
        issue.getReader().getIssues().remove(issue);
        repository.deleteById(uuid);
    }
}

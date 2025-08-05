package de.cwichlei.showcase.rest;

import de.cwichlei.showcase.persistence.Entity;
import de.cwichlei.showcase.persistence.Repo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

@RestController
@RequestMapping("/texts")
@RequiredArgsConstructor
@Slf4j
public class Controller {

    private final Repo repo;

    @GetMapping(path = "/{id}")
    public String findById(@PathVariable Long id) throws Throwable {
        Assert.notNull(id, "Id must be provided.");

        log.info("Finding text with id {}", id);

        Optional<Entity> result = repo.findById(id);

        return result
                .orElseThrow((Supplier<Throwable>) () -> new ResponseStatusException(HttpStatus.NOT_FOUND))
                .getText();
    }

    @GetMapping
    public Collection<Entity> findAll() {
        log.info("Finding all texts");

        return repo.findAll();
    }

    @PostMapping
    public Entity add(@RequestBody String text) {
        Assert.notNull(text, "Text must be provided.");

        log.info("Adding new text {}", text);

        Entity saved = repo.saveAndFlush(new Entity(text));

        log.info("Saved {}", saved);
        return saved;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Assert.notNull(id, "Id must be provided.");

        log.info("Deleting text with id {}", id);
        repo.deleteById(id);
    }
}

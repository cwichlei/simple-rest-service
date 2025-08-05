package de.cwichlei.showcase.rest;

import de.cwichlei.showcase.persistence.Entity;
import de.cwichlei.showcase.persistence.Repo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/texts")
@RequiredArgsConstructor
@Slf4j
public class Controller {

    private final Repo repo;
    private static final Entity DEFAULT = new Entity();

    @GetMapping(path = "/{id}")
    public String findById(@PathVariable Long id) {
        Assert.notNull(id, "Id must be provided.");

        log.info("Finding text with id {}", id);

        Optional<Entity> found = repo.findById(id);
        Entity result = found.orElse(DEFAULT);

        return result.getText();
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

    @DeleteMapping("/id")
    public void delete(@PathVariable Long id){
        Assert.notNull(id, "Id must be provided.");

        log.info("Deleting text with id {}", id);
        repo.deleteById(id);
    }
}

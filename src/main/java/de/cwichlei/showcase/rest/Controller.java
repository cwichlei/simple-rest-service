package de.cwichlei.showcase.rest;

import de.cwichlei.showcase.persistence.Entity;
import de.cwichlei.showcase.persistence.Repo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/texts")
@RequiredArgsConstructor
@Slf4j
public class Controller {

    private final Repo repo;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Entity> findById(@PathVariable Long id) {
        Assert.notNull(id, "Id must be provided.");

        log.info("Finding text with id {}", id);

        return ResponseEntity.of(repo.findById(id));
    }

    @GetMapping
    public ResponseEntity<Collection<Entity>> findAll() {
        log.info("Finding all texts");

        return ResponseEntity.ok().body(repo.findAll());
    }

    @PostMapping
    public ResponseEntity<Entity> add(@RequestBody Request request) {
        Assert.notNull(request, "Text must be provided.");

        log.info("Adding new text {}", request);

        Entity saved = repo.saveAndFlush(new Entity(request));

        log.info("Saved {}", saved);
        return ResponseEntity.ok().body(saved);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Assert.notNull(id, "Id must be provided.");

        log.info("Deleting text with id {}", id);
        repo.deleteById(id);
    }
}

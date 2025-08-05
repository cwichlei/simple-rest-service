package de.cwichlei.showcase.rest;

import de.cwichlei.showcase.persistence.Entity;
import de.cwichlei.showcase.persistence.Repo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ControllerIT {
    @Autowired
    private Controller underTest;

    @Autowired
    private Repo repo;

    @BeforeEach
    void init() {
        repo.deleteAll();
        repo.saveAndFlush(new Entity("test"));
    }

    @Test
    void testFindAll() {
        repo.saveAndFlush(new Entity("text"));

        Collection<Entity> result = underTest.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void testFindById() {
        Entity first = repo.findAll().getFirst();
        String result = underTest.findById(first.getId());

        assertThat(result).isEqualTo("test");
    }

    @Test
    void testAdd() {
        Entity result = underTest.add("text");

        assertThat(result.getText()).isEqualTo("text");
        assertThat(repo.findAll()).hasSize(2);
        assertThat(repo.findAll())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .isEqualTo(List.of(new Entity("test"), new Entity("text")));
    }

}
package de.cwichlei.showcase.rest;

import de.cwichlei.showcase.persistence.Entity;
import de.cwichlei.showcase.persistence.Repo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ControllerTest {

    @InjectMocks
    public Controller underTest;

    @Mock
    public Repo repo;

    @Captor
    public ArgumentCaptor<Entity> entityArgumentCaptor = ArgumentCaptor.forClass(Entity.class);

    @Captor
    public ArgumentCaptor<Long> idArgumentCaptor = ArgumentCaptor.forClass(Long.class);

    @Test
    void testFindById() {
        when(repo.findById(1L)).thenReturn(Optional.of(new Entity("Hello World")));

        String result = underTest.findById(1L);

        assertThat(result).isEqualTo("Hello World");
    }

    @Test
    void testFindAll() {
        List<Entity> all = List.of(new Entity("text 1"), new Entity("text 2"));
        when(repo.findAll()).thenReturn(all);

        Collection<Entity> result = underTest.findAll();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).containsAll(all);
    }

    @Test
    void testAdd() {
        when(repo.saveAndFlush(entityArgumentCaptor.capture())).thenReturn(new Entity("text"));

        Entity result = underTest.add("text");

        assertThat(result.getText()).isEqualTo("text");
        assertThat(entityArgumentCaptor.getValue().getText()).isEqualTo("text");
    }

    @Test
    void testDelete() {
        doNothing().when(repo).deleteById(idArgumentCaptor.capture());

        underTest.delete(1L);

        assertThat(idArgumentCaptor.getValue()).isEqualTo(1L);

    }
}
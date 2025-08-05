package de.cwichlei.showcase.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Repo extends JpaRepository<Entity, Long> {
}

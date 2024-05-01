package com.iago.passin.repositories;

import com.iago.passin.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, String> {
    Optional<Event> findByTitle(String title);
}

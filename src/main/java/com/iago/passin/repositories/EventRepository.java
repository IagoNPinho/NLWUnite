package com.iago.passin.repositories;

import com.iago.passin.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface EventRepository extends JpaRepository<Event, String> {}

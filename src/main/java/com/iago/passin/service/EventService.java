package com.iago.passin.service;

import com.iago.passin.domain.attendee.Attendee;
import com.iago.passin.domain.checkin.CheckIn;
import com.iago.passin.domain.checkin.exceptions.CheckInAlreadyExistsExceptions;
import com.iago.passin.domain.event.Event;
import com.iago.passin.domain.event.exceptions.EventFullException;
import com.iago.passin.domain.event.exceptions.EventHasAttendeeAlreadyCheckedIn;
import com.iago.passin.domain.event.exceptions.EventNotFoundException;
import com.iago.passin.dto.attendee.AttendeeDeleteDTO;
import com.iago.passin.dto.attendee.AttendeeIdDTO;
import com.iago.passin.dto.attendee.AttendeeRequestDTO;
import com.iago.passin.dto.event.EventIdDTO;
import com.iago.passin.dto.event.EventRequestDTO;
import com.iago.passin.dto.event.EventResponseDTO;
import com.iago.passin.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final AttendeeService attendeeService;
    private final ChekInService chekInService;

    // Retorna os detalhes de um evento.
    public EventResponseDTO getEventDetail(String eventId) {
        Event event = getEventById(eventId);
        List<Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(eventId);
        return new EventResponseDTO(event, attendeeList.size());
    }

    // Cria um novo evento
    public EventIdDTO createEvent(EventRequestDTO eventDTO) {
        Event newEvent = new Event();
        newEvent.setTitle(eventDTO.title());
        newEvent.setDetails(eventDTO.details());
        newEvent.setMaximumAttendees(eventDTO.maximumAttendees());
        newEvent.setSlug(this.createSlug(eventDTO.title()));

        this.eventRepository.save(newEvent);
        return new EventIdDTO(newEvent.getId());
    }

    // Registra um participante em um evento
    public AttendeeIdDTO registerAttendeeOnEvent(String eventId, AttendeeRequestDTO attendeeRequestDTO) {
        this.attendeeService.verifyAttendeeSubscription(attendeeRequestDTO.email(), eventId);

        Event event = getEventById(eventId);
        List<Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(eventId);

        if (event.getMaximumAttendees() <= attendeeList.size()) throw new EventFullException("Event is full");

        Attendee newAttendee = new Attendee(attendeeRequestDTO.name(), attendeeRequestDTO.email(), event, LocalDateTime.now());

        this.attendeeService.registerAttendee(newAttendee);

        return new AttendeeIdDTO(newAttendee.getId());
    }

    // Atualiza os dados do evento e retorna os dados completos do mesmo
    public EventResponseDTO updateEvent(String eventId, EventRequestDTO eventRequestDTO){
        Event event = this.getEventById(eventId);

        this.attendeesAlreadyCheckedInOnEvent(eventId);

        Event newEvent = new Event(event.getId(), eventRequestDTO.title(), eventRequestDTO.details(), this.createSlug(eventRequestDTO.title()), eventRequestDTO.maximumAttendees());

        this.eventRepository.save(newEvent);

        return new EventResponseDTO(newEvent, this.attendeeService.getAllAttendeesFromEvent(eventId).size());
    }

    // Verifica se já tem algum participante do evento que realizou check-in
    private void attendeesAlreadyCheckedInOnEvent(String eventId){
        List<Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(eventId);

        for(Attendee attendee : attendeeList){
            Optional<CheckIn> isCheckedIn = this.chekInService.getCheckIn(attendee.getId());
            if(isCheckedIn.isPresent()) throw new EventHasAttendeeAlreadyCheckedIn("Attendees on this event have already checked in");
        }
    }

    // Remove um participante do evento
    public AttendeeDeleteDTO deleteAttendeeOnEvent(String eventId, String attendeeId){
        Attendee attendee = this.attendeeService.getAttendee(attendeeId);
        Event event = this.getEventById(eventId);

        Optional<CheckIn> isCheckedIn = this.chekInService.getCheckIn(attendeeId);

        if(isCheckedIn.isPresent()) throw new CheckInAlreadyExistsExceptions("Attendee cannot be excluded because they already checked in");

        this.attendeeService.deleteAttendee(attendee);

        return new AttendeeDeleteDTO(attendee.getId(), attendee.getName(), event.getId());
    }

    // Retorna um evento com base no ID
    private Event getEventById(String eventId){
        return this.eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + eventId));
    }

    // Cria o Slug do evento com base um algumas regex's
    private String createSlug(String text) {
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("\\s+", "-")
                .toLowerCase();
    }
}

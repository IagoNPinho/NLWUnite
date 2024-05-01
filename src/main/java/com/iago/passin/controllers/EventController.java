package com.iago.passin.controllers;


import com.iago.passin.dto.attendee.AttendeeDeleteDTO;
import com.iago.passin.dto.attendee.AttendeeIdDTO;
import com.iago.passin.dto.attendee.AttendeeRequestDTO;
import com.iago.passin.dto.attendee.AttendeesListResponseDTO;
import com.iago.passin.dto.event.EventDeleteResponseDTO;
import com.iago.passin.dto.event.EventIdDTO;
import com.iago.passin.dto.event.EventRequestDTO;
import com.iago.passin.dto.event.EventResponseDTO;
import com.iago.passin.service.AttendeeService;
import com.iago.passin.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final AttendeeService attendeeService;

    // Endpoint que retorna o evento existente
    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String eventId){
        EventResponseDTO event = this.eventService.getEventDetail(eventId);
        return ResponseEntity.ok(event);
    }

    // Endpoint que cria o evento
    @PostMapping
    public ResponseEntity<EventIdDTO> createEvent(@RequestBody EventRequestDTO body, UriComponentsBuilder uriComponentsBuilder){
        EventIdDTO eventIdDTO = this.eventService.createEvent(body);

        URI uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(eventIdDTO.eventId()).toUri();

        return ResponseEntity.created(uri).body(eventIdDTO);
    }

    // Endpoint que atualiza os dados do evento evento
    @PutMapping("/{eventId}/update")
    public ResponseEntity<EventResponseDTO> updateEvent(@PathVariable String eventId, @RequestBody EventRequestDTO body){
        EventResponseDTO eventResponseDTO = this.eventService.updateEvent(eventId, body);

        return ResponseEntity.ok(eventResponseDTO);
    }

    // Endpoint que retorna os participantes do evento
    @GetMapping("/attendees/{eventId}")
    public ResponseEntity<AttendeesListResponseDTO> getEventAttendees(@PathVariable String eventId){
        AttendeesListResponseDTO attendeesListResponse = this.attendeeService.getEventsAttendee(eventId);
        return ResponseEntity.ok(attendeesListResponse);
    }

    // Endpoint para registrar o participante
    @PostMapping("/{eventId}/attendees")
    public ResponseEntity<AttendeeIdDTO> registerParticipant(@PathVariable String eventId, @RequestBody AttendeeRequestDTO body, UriComponentsBuilder uriComponentsBuilder){
        AttendeeIdDTO attendeeIdDTO = this.eventService.registerAttendeeOnEvent(eventId, body);

        URI uri = this.attendeeService.getUriBadge(uriComponentsBuilder, attendeeIdDTO.attendeeId());

        return ResponseEntity.created(uri).body(attendeeIdDTO);
    }

    // Endpoint para remover o participante
    @DeleteMapping("/{eventId}/attendee/{attendeeId}/delete")
    public ResponseEntity<AttendeeDeleteDTO> deleteParticipant(@PathVariable String eventId, @PathVariable String attendeeId){
        AttendeeDeleteDTO attendeeDeleteDTO = this.eventService.deleteAttendeeOnEvent(eventId, attendeeId);

        return ResponseEntity.ok(attendeeDeleteDTO);
    }

    // Endpoint para apagar o evento
    @DeleteMapping("{eventId}/delete")
    public ResponseEntity<EventDeleteResponseDTO> deleteEvent(@PathVariable String eventId, UriComponentsBuilder uriComponentsBuilder){
        EventDeleteResponseDTO eventDTO = this.eventService.deleteEvent(eventId, uriComponentsBuilder);

        return ResponseEntity.ok(eventDTO);
    }
}

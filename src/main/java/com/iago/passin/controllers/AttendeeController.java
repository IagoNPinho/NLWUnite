package com.iago.passin.controllers;

import com.iago.passin.dto.attendee.AttendeeBadgeResponseDTO;
import com.iago.passin.service.AttendeeService;
import com.iago.passin.service.ChekInService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/attendees")
@RequiredArgsConstructor
public class AttendeeController {
    private final AttendeeService attendeeService;

    // Endpoint que retorna o crachá do participante
    @GetMapping("/{attendeeId}/badge")
    public ResponseEntity<AttendeeBadgeResponseDTO> getAttendeeBadge(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder){
        AttendeeBadgeResponseDTO responseDTO = this.attendeeService.getAttendeeBadge(attendeeId, uriComponentsBuilder);
        return ResponseEntity.ok(responseDTO);
    }

    // Endpoint que realizar o checkin do participante no evento
    @PostMapping("/{attendeeId}/check-in")
    public ResponseEntity<URI> registerCheckIn(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder){
        this.attendeeService.checkInAttendee(attendeeId);

        URI uri = uriComponentsBuilder.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeId).toUri();

        return ResponseEntity.created(uri).build();

    }
}
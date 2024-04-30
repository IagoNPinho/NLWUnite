package com.iago.passin.controllers;

import com.iago.passin.dto.attendee.AttendeeBadgeResponseDTO;
import com.iago.passin.dto.attendee.AttendeeRequestDTO;
import com.iago.passin.dto.attendee.AttendeeUpdateResponseDTO;
import com.iago.passin.service.AttendeeService;
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

        URI uri = this.attendeeService.getUriBadge(uriComponentsBuilder, attendeeId);

        return ResponseEntity.created(uri).build();
    }

    // Endpoint que atualiza os dados do participante
    @PutMapping("/{attendeeId}/update")
    public ResponseEntity<AttendeeUpdateResponseDTO> updateAttendee(@PathVariable String attendeeId, @RequestBody AttendeeRequestDTO body, UriComponentsBuilder uriComponentsBuilder){
        AttendeeUpdateResponseDTO responseDTO = this.attendeeService.updateAttendee(attendeeId, body, uriComponentsBuilder);

        return ResponseEntity.ok(responseDTO);
    }
}
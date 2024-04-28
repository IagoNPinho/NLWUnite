package com.iago.passin.service;

import com.iago.passin.domain.attendee.Attendee;
import com.iago.passin.domain.attendee.execeptions.AttendeeAlreadyExistException;
import com.iago.passin.domain.attendee.execeptions.AttendeeNotFoundException;
import com.iago.passin.domain.checkin.CheckIn;
import com.iago.passin.dto.attendee.*;
import com.iago.passin.repositories.AttendeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {
    private final AttendeeRepository attendeeRepository;
    private final ChekInService chekInService;

    // Retorna os participantes de um evento
    public List<Attendee> getAllAttendeesFromEvent(String eventId){
        return this.attendeeRepository.findByEventId(eventId);
    }

    // Retorna o DTO dos participantes de um evento
    public AttendeesListResponseDTO getEventsAttendee(String eventId){
        List<Attendee> attendeeList = this.getAllAttendeesFromEvent(eventId);

        List<AttendeeDetails> attendeeDetailsList = attendeeList.stream().map(attendee -> {
            Optional<CheckIn> checkIn = this.chekInService.getCheckIn(attendee.getId());
            LocalDateTime checkedInAt = checkIn.<LocalDateTime>map(CheckIn::getCreatedAt).orElse(null);
            return new AttendeeDetails(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreateAt(), checkedInAt);
        }).toList();

        return new AttendeesListResponseDTO(attendeeDetailsList);
    }

    // Verifica se o participante já está inscrito
    public void verifyAttendeeSubscription(String email, String eventId){
        Optional<Attendee> isAttendeeRegistered = this.attendeeRepository.findByEventIdAndEmail(eventId, email);
        if(isAttendeeRegistered.isPresent())throw new AttendeeAlreadyExistException("Attendee is already registered!");
    }

    // Registra o participante
    public Attendee registerAttendee(Attendee newAttendee){
        this.attendeeRepository.save(newAttendee);
        return newAttendee;
    }

    // Remove o participante
    public void deleteAttendee(Attendee attendee){
        this.attendeeRepository.delete(attendee);
    }

    // Retorna o crachá do participante
    public AttendeeBadgeResponseDTO getAttendeeBadge(String attendeeId, UriComponentsBuilder uriComponentsBuilder){
        Attendee attendee = this.getAttendee(attendeeId);

        String uri = uriComponentsBuilder.path("/attendees/{attendeeId}/check-in").buildAndExpand(attendeeId).toUri().toString();

        AttendeeBadgeDTO badgeDTO = new AttendeeBadgeDTO(attendee.getName(), attendee.getEmail(), uri, attendee.getEvent().getId());
        return new AttendeeBadgeResponseDTO(badgeDTO);
    }

    // Registra o check in através do CheckInService
    public void checkInAttendee(String attendeeId){
        Attendee attendee = this.getAttendee(attendeeId);

        this.chekInService.registerCheckIn(attendee);
    }

    // Retorna o participante pelo id ou lança uma exceção de participante não encontrado.
    public Attendee getAttendee(String attendeeId){
        return this.attendeeRepository.findById(attendeeId).orElseThrow(() -> new AttendeeNotFoundException("attendee not found with ID: " + attendeeId));
    }
}

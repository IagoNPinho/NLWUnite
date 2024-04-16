package com.iago.passin.config;

import com.iago.passin.domain.attendee.execeptions.AttendeeAlreadyExistException;
import com.iago.passin.domain.attendee.execeptions.AttendeeNotFoundException;
import com.iago.passin.domain.checkin.exceptions.CheckInAlreadyExistsExceptions;
import com.iago.passin.domain.event.exceptions.EventFullException;
import com.iago.passin.domain.event.exceptions.EventNotFoundException;
import com.iago.passin.dto.general.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionEntityHandler {
    @ExceptionHandler(EventFullException.class)
    public ResponseEntity<ErrorResponseDTO> handleEventFull(EventFullException exception){
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity handleEventNotFound(EventNotFoundException exception){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AttendeeNotFoundException.class)
    public ResponseEntity handleAttendeeNotFound(AttendeeNotFoundException exception){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AttendeeAlreadyExistException.class)
    public ResponseEntity handleAttendeeAlreadyExist(AttendeeAlreadyExistException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(CheckInAlreadyExistsExceptions.class)
    public ResponseEntity handleCheckInAlreadyExist(CheckInAlreadyExistsExceptions exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDTO(exception.getMessage()));
    }
}

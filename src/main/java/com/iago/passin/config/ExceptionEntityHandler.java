package com.iago.passin.config;

import com.iago.passin.domain.attendee.execeptions.AttendeeAlreadyExistException;
import com.iago.passin.domain.attendee.execeptions.AttendeeNotFoundException;
import com.iago.passin.domain.checkin.exceptions.CheckInAlreadyExistsExceptions;
import com.iago.passin.domain.event.exceptions.EventAlreadyExistException;
import com.iago.passin.domain.event.exceptions.EventFullException;
import com.iago.passin.domain.event.exceptions.EventAttendeeCheckedInException;
import com.iago.passin.domain.event.exceptions.EventNotFoundException;
import com.iago.passin.dto.general.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionEntityHandler {

    // Event Exception
    @ExceptionHandler(EventFullException.class)
    public ResponseEntity<ErrorResponseDTO> handleEventFull(EventFullException exception){
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
    }

    // Event Exception
    @ExceptionHandler(EventAttendeeCheckedInException.class)
    public ResponseEntity handleEventHasAttendeeAlreadyCheckedIn(EventAttendeeCheckedInException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDTO(exception.getMessage()));
    }

    // Event Exception
    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity handleEventNotFound(EventNotFoundException exception){
        return ResponseEntity.notFound().build();
    }

    // Event Exception
    @ExceptionHandler(EventAlreadyExistException.class)
    public ResponseEntity handleEventAlreadyExist(EventAlreadyExistException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDTO(exception.getMessage()));
    }

    // Attendee Exception
    @ExceptionHandler(AttendeeAlreadyExistException.class)
    public ResponseEntity handleAttendeeAlreadyExist(AttendeeAlreadyExistException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDTO(exception.getMessage()));
    }

    // Attendee Exception
    @ExceptionHandler(AttendeeNotFoundException.class)
    public ResponseEntity handleAttendeeNotFound(AttendeeNotFoundException exception){
        return ResponseEntity.notFound().build();
    }

    // CheckIn Exception
    @ExceptionHandler(CheckInAlreadyExistsExceptions.class)
    public ResponseEntity handleCheckInAlreadyExist(CheckInAlreadyExistsExceptions exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDTO(exception.getMessage()));
    }
}

package com.iago.passin.domain.attendee.execeptions;

public class AttendeeAlreadyExistException extends RuntimeException {
    public AttendeeAlreadyExistException (String message){
        super(message);
    }
}

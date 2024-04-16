package com.iago.passin.domain.attendee.execeptions;

public class AttendeeNotFoundException extends RuntimeException {
    public AttendeeNotFoundException(String message){
        super(message);
    }
}

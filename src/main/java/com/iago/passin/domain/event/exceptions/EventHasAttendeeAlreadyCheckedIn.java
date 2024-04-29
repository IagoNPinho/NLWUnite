package com.iago.passin.domain.event.exceptions;

public class EventHasAttendeeAlreadyCheckedIn extends RuntimeException{
    public EventHasAttendeeAlreadyCheckedIn(String message){
        super(message);
    }
}

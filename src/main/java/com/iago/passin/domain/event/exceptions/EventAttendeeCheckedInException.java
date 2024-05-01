package com.iago.passin.domain.event.exceptions;

public class EventAttendeeCheckedInException extends RuntimeException{
    public EventAttendeeCheckedInException(String message){
        super(message);
    }
}

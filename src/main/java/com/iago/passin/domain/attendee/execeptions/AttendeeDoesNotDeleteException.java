package com.iago.passin.domain.attendee.execeptions;

public class AttendeeDoesNotDeleteException extends RuntimeException{
    public AttendeeDoesNotDeleteException (String message){
        super(message);
    }

}

package com.iago.passin.domain.checkin.exceptions;

public class CheckInAlreadyExistsExceptions extends RuntimeException{
    public CheckInAlreadyExistsExceptions(String message){
        super(message);
    }
}

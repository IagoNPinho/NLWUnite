package com.iago.passin.dto.attendee;

import java.time.LocalDateTime;

public record AttendeeDetailsDTO(String id, String name, String email, LocalDateTime createdAt, LocalDateTime checkInAt){
}

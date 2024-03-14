package com.practice.util;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
public class ErrorDetaile {
    private HttpStatus status;
    private String message;
    private LocalDateTime dataAndTime;
    private String uri;
}

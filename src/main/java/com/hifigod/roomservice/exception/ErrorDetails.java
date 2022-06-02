package com.hifigod.roomservice.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

/**
 * This class holds the set of information that need to pass when an error occurred.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ErrorDetails {

    private int status;
    private String error;
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime dateTime;

}

package com.hifigod.roomservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalTime;

@ApiModel(description = "Details about the available time of the room")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RoomAvailabilityDto {

    @ApiModelProperty(notes = "Day of the week")
    private String day;

    @ApiModelProperty(notes = "Start time in format of HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime startTime;

    @ApiModelProperty(notes = "End time in format of HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime endTime;
}

package com.hifigod.roomservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalTime;

@Data
@ApiModel(description = "Details about the rooms' availability")
public class RoomAvailabilityDto {

    @ApiModelProperty(notes = "Day of the week")
    private String day;

    @ApiModelProperty(notes = "Morning or Evening")
    private String session;

    @ApiModelProperty(notes = "Session start time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime startTime;

    @ApiModelProperty(notes = "Session end time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime endTime;
}

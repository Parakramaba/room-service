package com.hifigod.roomservice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@ApiModel(description = "Update details about the room")
@AllArgsConstructor
@NoArgsConstructor
public class RoomUpdateDto {

    @ApiModelProperty(notes = "The room name")
    private String name;

    @ApiModelProperty(notes = "Detailed room description")
    private String description;

    @ApiModelProperty(notes = "Number of guests which room can contain")
    private Integer noOfGuest;

    @ApiModelProperty(notes = "Hourly rate")
    private Integer hourlyRate;

    private Double latitude;

    private Double longitude;

    private String country;

    private String streetAddress;

    private String apartmentNo;

    private String city;

    private String postCode;

    private Long setupCost;

    @ApiModelProperty(notes = "Set of room amenity ids")
    private List<String> amenitiesIdList;

    @ApiModelProperty(notes = "Available time slots of the room")
    private List<RoomAvailabilityDto> roomAvailabilities;


}

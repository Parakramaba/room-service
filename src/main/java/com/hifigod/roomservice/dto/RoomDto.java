package com.hifigod.roomservice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "Details about the room")
public class RoomDto {

    @ApiModelProperty(notes = "The room name")
    private String name;

    @ApiModelProperty(notes = "Detailed room description")
    private String description;

    @ApiModelProperty(notes = "User id of the room creator")
    private String userId;

    @ApiModelProperty(notes = "Room type id")
    private String roomTypeId;

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

package com.hifigod.roomservice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Details about the room")
public class RoomDto {

    @ApiModelProperty(notes = "The room name")
    private String name;

    @ApiModelProperty(notes = "Detailed room description")
    private String description;

    @ApiModelProperty(notes = "User id of the room creator")
    private long userId;

    @ApiModelProperty(notes = "Room type id")
    private int roomTypeId;

    @ApiModelProperty(notes = "Number of guests which room can contain")
    private int noOfGuest;

    @ApiModelProperty(notes = "Hourly rate")
    private int hourlyRate;

//    private Double latitude;
//
//    private Double longitude;
//
//    private int countryId;
//
//    private String streetAddress;
//
//    private String apartmentNo;
//
//    private String city;
//
//    private String postCode;
//
//    private long setupCost;




}

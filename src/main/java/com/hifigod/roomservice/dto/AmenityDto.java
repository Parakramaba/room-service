package com.hifigod.roomservice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Details about the amenity")
public class AmenityDto {

    @ApiModelProperty(notes = "The amenity name")
    private String name;
}

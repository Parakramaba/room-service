package com.hifigod.roomservice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(description = "Details about the amenity")
@AllArgsConstructor
@NoArgsConstructor
public class AmenityDto {

    @ApiModelProperty(notes = "The amenity name")
    private String name;
}

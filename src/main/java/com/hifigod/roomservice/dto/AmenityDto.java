package com.hifigod.roomservice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(description = "Details about the amenity")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AmenityDto {

    @ApiModelProperty(notes = "The amenity name")
    private String name;
}

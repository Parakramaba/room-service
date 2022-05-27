package com.hifigod.roomservice.controller;

import com.hifigod.roomservice.exception.ResourceNotFoundException;
import com.hifigod.roomservice.service.AmenityService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This Controller class provides set of API endpoints which are using to retrieve amenities
 */
@RestController
@RequestMapping("/api/v1/rooms/amenities")
public class AmenityController {

    // INJECT SERVICE OBJECT DEPENDENCY
    @Autowired
    private AmenityService amenityService;


    @ApiOperation(value = "Get all amenities",
            notes = "Return all available amenities")
    @GetMapping("/all")
    public ResponseEntity<?> getAllAmenities() {
        return amenityService.getAllAmenities();
    }


}

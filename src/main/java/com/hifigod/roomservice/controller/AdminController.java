package com.hifigod.roomservice.controller;

import com.hifigod.roomservice.dto.AmenityDto;
import com.hifigod.roomservice.exception.ResourceNotFoundException;
import com.hifigod.roomservice.exception.ValidationException;
import com.hifigod.roomservice.service.AdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rooms/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // ROOM RELATED ENDPOINTS
    @ApiOperation(value = "Get all deleted rooms")
    @GetMapping("/deleted-rooms")
    public ResponseEntity<?> getAllDeletedRooms() throws ResourceNotFoundException {
        return adminService.getAllDeletedRooms();
    }

    // AMENITY RELATED ENDPOINTS
    @ApiOperation(value = "Create a new amenity",
            notes = "Provide valid amenity name")
    @PostMapping("/amenity/new")
    public ResponseEntity<String> createAmenity(@RequestBody AmenityDto amenityDto) throws ValidationException {
        return adminService.createAmenity(amenityDto);
    }




}

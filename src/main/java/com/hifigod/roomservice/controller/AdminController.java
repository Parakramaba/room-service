package com.hifigod.roomservice.controller;

import com.hifigod.roomservice.dto.AmenityDto;
import com.hifigod.roomservice.exception.ValidationException;
import com.hifigod.roomservice.service.AdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This Controller class provides set of API endpoints which are using in the system admin related matters
 */
@RestController
@RequestMapping("/api/v1/rooms/admin")
public class AdminController {

    // INJECT SERVICE OBJECT DEPENDENCY
    @Autowired
    private AdminService adminService;

    // ROOM RELATED ENDPOINTS
    @ApiOperation(value = "Get all deleted rooms")
    @GetMapping("/deleted-rooms")
    public ResponseEntity<?> getAllDeletedRooms() {
        return adminService.getAllDeletedRooms();
    }

    // AMENITY RELATED ENDPOINTS
    @ApiOperation(value = "Create a new amenity",
            notes = "Provide valid amenity name")
    @PostMapping("/amenity/new")
    public ResponseEntity<?> createAmenity(final @RequestBody AmenityDto amenityDto) throws ValidationException {
        return adminService.createAmenity(amenityDto);
    }




}

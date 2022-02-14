package com.hifigod.roomservice.controller;

import com.hifigod.roomservice.dto.Response;
import com.hifigod.roomservice.dto.RoomDto;
import com.hifigod.roomservice.exception.ResourceNotFoundException;
import com.hifigod.roomservice.exception.ValidationException;
import com.hifigod.roomservice.model.Room;
import com.hifigod.roomservice.service.RoomService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping("/new")
    @ApiOperation(value = "Create a new room",
            notes = "Provide valid room details to create a new room",
            response = Response.class)
    public ResponseEntity<?> createListeningRoom(@RequestBody RoomDto roomDto) throws ResourceNotFoundException,
            ValidationException {
        return roomService.createRoom(roomDto);
    }


    @ApiOperation(value = "Get all rooms",
                notes = "Return all available rooms")
    @GetMapping("/all")
    public ResponseEntity<?> getAllRooms() throws ResourceNotFoundException {
        return roomService.getAllRooms();
    }


    @ApiOperation(value = "Get all amenities",
                notes = "Return all available amenities")
    @GetMapping("/amenities-list")
    public ResponseEntity<?> getAllAmenities() throws ResourceNotFoundException {
        return roomService.getAllAmenities();
    }

//    @ApiOperation(value = "Get room amenities",
//                notes = "Return amenities of the room")
//    @GetMapping("/amenities")
//    public ResponseEntity<?> getRoomAmenities(@RequestBody Room room) {
//        return roomService.getRoomAmenities(room);
//    }


}

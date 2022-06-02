package com.hifigod.roomservice.controller;

import com.hifigod.roomservice.dto.Response;
import com.hifigod.roomservice.dto.RoomDto;
import com.hifigod.roomservice.dto.RoomUpdateDto;
import com.hifigod.roomservice.exception.ResourceNotFoundException;
import com.hifigod.roomservice.exception.ValidationException;
import com.hifigod.roomservice.service.RoomService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This Controller class provides set of API endpoints which are using in the Room handling process.
 */
@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    // INJECT SERVICE OBJECT DEPENDENCY
    @Autowired
    private RoomService roomService;


    @PostMapping("/new")
    @ApiOperation(value = "Create a new room",
            notes = "Provide valid room details to create a new room",
            response = Response.class)
    public ResponseEntity<?> createListeningRoom(final @RequestBody RoomDto roomDto) throws ResourceNotFoundException,
            ValidationException {
        return roomService.createRoom(roomDto);
    }

    @ApiOperation(value = "Get all rooms",
                notes = "Return all available rooms")
    @GetMapping("/all")
    public ResponseEntity<?> getAllRooms() {
        return roomService.getAllRooms();
    }

    @ApiOperation(value = "Get the room by id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomById(final @PathVariable("id") String roomId) throws ResourceNotFoundException {
        return roomService.getRoomById(roomId);
    }

    @ApiOperation(value = "Get rooms by the type")
    @GetMapping("/by-type/{roomType}")
    public ResponseEntity<?> getRoomsByType(final @PathVariable("roomType") String roomTypeId)
            throws ResourceNotFoundException {
        return roomService.getRoomsByType(roomTypeId);
    }

    @ApiOperation(value = "Update the room details",
            notes = "Provide valid room details to update")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateRoom(final @PathVariable("id") String roomId,
            final @RequestBody RoomUpdateDto roomUpdateDto) throws ResourceNotFoundException {
        return roomService.updateRoom(roomId, roomUpdateDto);
    }

    @ApiOperation(value = "Delete the room by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoom(final @PathVariable("id") String roomId) throws ResourceNotFoundException {
        return roomService.deleteRoom(roomId);
    }

    @ApiOperation(value = "Get all rooms owned by a user")
    @GetMapping("/of-user/{userId}")
    public ResponseEntity<?> getRoomsByUser(final @PathVariable("userId") String userId)
            throws ResourceNotFoundException {
        return roomService.getRoomsByUser(userId);
    }

    @ApiOperation(value = "Search the room by keyword",
    notes = "Return all rooms that match name, description, country or city with the keywords' consistent words")
    @GetMapping("/search/{keyword}")
    public ResponseEntity<?> searchRoom(final @PathVariable("keyword") String keyword) {
        return roomService.searchRoom(keyword);
    }

//    @ApiOperation(value = "Get room amenities",
//                notes = "Return amenities of the room")
//    @GetMapping("/{id}/amenities")
//    public ResponseEntity<?> getRoomAmenities(@PathVariable("id") String roomId) {
//        return roomService.getRoomAmenities(roomId);
//    }
}

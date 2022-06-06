package com.hifigod.roomservice.service;

import com.hifigod.roomservice.dto.AmenityDto;
import com.hifigod.roomservice.dto.Response;
import com.hifigod.roomservice.exception.ResourceNotFoundException;
import com.hifigod.roomservice.exception.ValidationException;
import com.hifigod.roomservice.model.Amenity;
import com.hifigod.roomservice.model.Room;
import com.hifigod.roomservice.repository.AmenityRepository;
import com.hifigod.roomservice.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * This Service class implements the business logic of the endpoints which are provided in the AdminController.
 * */
@Service("AdminService")
public class AdminService {

    // INJECT REPOSITORY OBJECT DEPENDENCIES
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private AmenityRepository amenityRepository;
    // / INJECT REPOSITORY OBJECT DEPENDENCIES

    // HANDLING ROOM DATA
    /**
     * This returns a ResponseEntity with the List of deleted rooms.
     * @return List of deleted rooms
     */
    public ResponseEntity<?> getAllDeletedRooms() {
        List<Room> rooms = roomRepository.findAllByIsDeletedTrue();
        if (rooms.isEmpty()) {
            return new ResponseEntity<>("There are no deleted rooms found", HttpStatus.OK);
        }

        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }


    // HANDLING AMENITY DATA
    public ResponseEntity<?> createAmenity(final AmenityDto amenityDto) throws ValidationException {
        if (amenityDto.getName() == null || amenityDto.getName().length() == 0) {
            throw new ValidationException("Amenity name is required");
        }
        Amenity amenity = new Amenity();
        UUID amenityId = UUID.randomUUID();
        amenity.setId(amenityId.toString());
        amenity.setName(amenityDto.getName());
        amenityRepository.save(amenity);

        // Response
        Response response = new Response();
        response.setStatus(HttpStatus.OK.value());
        response.setError("");
        response.setMessage("Amenity created Successfully");
        response.setDateTime(LocalDateTime.now());
        response.setData(amenity.getId());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}

package com.hifigod.roomservice.service;

import com.hifigod.roomservice.dto.AmenityDto;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("AdminService")
public class AdminService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private AmenityRepository amenityRepository;

    // HANDLING ROOM DATA
    public ResponseEntity<?> getAllDeletedRooms() throws ResourceNotFoundException {
        ArrayList<Optional<Room>> rooms = roomRepository.findAllByDeletedTrue();
        if(rooms.isEmpty())
            throw new ResourceNotFoundException("There are no deleted rooms found");
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }


    // HANDLING AMENITY DATA
    public ResponseEntity<String> createAmenity(AmenityDto amenityDto) throws ValidationException {
        if(amenityDto.getName() == null || amenityDto.getName().length() == 0)
            throw new ValidationException("Amenity name is required");
        Amenity amenity = new Amenity();
        UUID amenityId = UUID.randomUUID();
        amenity.setId(amenityId.toString());
        amenity.setName(amenityDto.getName());
        amenityRepository.save(amenity);

        return new ResponseEntity<>("Amenity created successfully", HttpStatus.OK);
    }



}

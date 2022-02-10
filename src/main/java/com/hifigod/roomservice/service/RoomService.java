package com.hifigod.roomservice.service;

import com.hifigod.roomservice.dto.Response;
import com.hifigod.roomservice.dto.RoomDto;
import com.hifigod.roomservice.exception.ResourceNotFoundException;
import com.hifigod.roomservice.exception.ValidationException;
import com.hifigod.roomservice.model.*;
import com.hifigod.roomservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service("RoomService")
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private RoomDocumentRepository roomDocumentRepository;

    @Autowired
    private AmenityRepository amenityRepository;

    @Autowired
    private RoomAvailabilityRepository roomAvailabilityRepository;


    public ResponseEntity<?> createRoom(RoomDto roomDto) throws ResourceNotFoundException,
            ValidationException {
        if(roomDto.getName() == null || roomDto.getName().length() == 0)
            throw new ValidationException("Room name required");

//        if(roomDto.getHourlyRate() < 0)
//            throw new ValidationException("Hourly rate should be zero or positive number");
//        if(roomDto.getNoOfGuest() < 0)
//            throw new ValidationException("Number of guest should be zero or positive number");

        User user = userRepository.findById(roomDto.getUserId()).
                orElseThrow(() -> new ResourceNotFoundException("User not found : " + roomDto.getUserId()));
        RoomType roomType = roomTypeRepository.findById(roomDto.getRoomTypeId()).
                orElseThrow(()-> new ResourceNotFoundException("Room type not found : " + roomDto.getRoomTypeId()));

        Room room = new Room();
        room.setUser(user);
        room.setRoomType(roomType);
        room.setName(roomDto.getName());
        room.setDescription(roomDto.getDescription());
        room.setHourlyRate(roomDto.getHourlyRate());
        room.setNoOfGuest(roomDto.getNoOfGuest());

        // TODO : room images uploading need to be handle

        room.setCountry(roomDto.getCountry());
        room.setStreetAddress(roomDto.getStreetAddress());
        room.setApartmentNo(roomDto.getApartmentNo());
        room.setCity(roomDto.getCity());
        room.setPostCode(roomDto.getPostCode());

        room.setLatitude(roomDto.getLatitude());
        room.setLongitude(roomDto.getLongitude());
        room.setSetupCost(roomDto.getSetupCost());
        room.setCreatedOn(LocalDateTime.now());

        // Insert room amenities
        ArrayList<Amenity> amenitiesList = new ArrayList<Amenity>();
        for (Integer amenityId:
                roomDto.getAmenitiesIdList()) {
            Amenity amenity = amenityRepository.findById(amenityId).orElseThrow(()
                    -> new ResourceNotFoundException("Amenity not found : " + amenityId));
            amenitiesList.add(amenity);
        }
        room.setAmenities(amenitiesList);
        roomRepository.save(room);

        // TODO : availabilities need to be handled
        // Insert room availabilities

//        for (RoomAvailability availableSlot:
//                roomDto.getRoomAvailabilities()) {
//
//            RoomAvailability roomAvailability = new RoomAvailability();
//            roomAvailability.setRoom(availableSlot.getRoom());
//            roomAvailability.setDay(availableSlot.getDay());
//            roomAvailability.setSession(availableSlot.getSession());
//            roomAvailability.setStartTime(availableSlot.getStartTime());
//            roomAvailability.setEndTime(availableSlot.getEndTime());
//            roomAvailabilityRepository.save(roomAvailability);
//        }

        // TODO : setup information need to be handled


        Response response = new Response();
        response.setStatus(HttpStatus.OK.value());
        response.setError("");
        response.setMessage("Room created Successfully");
        response.setDateTime(LocalDateTime.now());
        response.setData(null);

        return new ResponseEntity<> (response, HttpStatus.OK);
    }

    public ResponseEntity<?> getAllRooms() throws ResourceNotFoundException {
        if(roomRepository.findAll().isEmpty())
            throw new ResourceNotFoundException("There are no rooms found");
        return new ResponseEntity<>(roomRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<?> getAllAmenities() throws ResourceNotFoundException {
        if(amenityRepository.findAll().isEmpty())
            throw new ResourceNotFoundException("There are no amenities found");
        return new ResponseEntity<>(amenityRepository.findAll(), HttpStatus.OK);
    }


}

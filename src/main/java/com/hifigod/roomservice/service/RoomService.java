package com.hifigod.roomservice.service;

import com.hifigod.roomservice.dto.Response;
import com.hifigod.roomservice.dto.RoomAvailabilityDto;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("RoomService")
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

//    @Autowired
//    private RoomDocumentRepository roomDocumentRepository;

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
                orElseThrow(() -> new ResourceNotFoundException("Room type not found : " + roomDto.getRoomTypeId()));

        // TODO: validate the name with userId uniqueness

//        Optional<Room> optionalRoom = roomRepository.findByNameAndUserId(roomDto.getName(), user.getUserId());
//
//        if(optionalRoom.isPresent())
//            throw new ValidationException("You have a room with the same name.Please give different name");

        UUID roomId = UUID.randomUUID();
//        String roomIdAsString = roomId.toString();
        Room room = new Room();
        room.setId(roomId.toString());
        room.setUser(user);
        room.setRoomType(roomType);
        room.setName(roomDto.getName());
        room.setDescription(roomDto.getDescription());
        room.setHourlyRate(roomDto.getHourlyRate());
        room.setNoOfGuest(roomDto.getNoOfGuest());

        // TODO : room image uploading need to be handle

        room.setCountry(roomDto.getCountry());
        room.setStreetAddress(roomDto.getStreetAddress());
        room.setApartmentNo(roomDto.getApartmentNo());
        room.setCity(roomDto.getCity());
        room.setPostCode(roomDto.getPostCode());

        room.setLatitude(roomDto.getLatitude());
        room.setLongitude(roomDto.getLongitude());
        room.setSetupCost(roomDto.getSetupCost());

        // Insert room amenities
        ArrayList<Amenity> amenitiesList = new ArrayList<>();
        for (String amenityId:
                roomDto.getAmenitiesIdList()) {
            Amenity amenity = amenityRepository.findById(amenityId).orElseThrow(()
                    -> new ResourceNotFoundException("Amenity not found : " + amenityId));
            amenitiesList.add(amenity);
        }
        room.setAmenities(amenitiesList);
        roomRepository.save(room);

        // Insert room availabilities

        for (RoomAvailabilityDto availableSlot:
                roomDto.getRoomAvailabilities()) {
                UUID roomAvailabilityId = UUID.randomUUID();
            RoomAvailability roomAvailability = new RoomAvailability();
            roomAvailability.setId(roomAvailabilityId.toString());
            roomAvailability.setRoom(room);
            roomAvailability.setDay(availableSlot.getDay());
            roomAvailability.setSession(availableSlot.getSession());
            roomAvailability.setStartTime(availableSlot.getStartTime());
            roomAvailability.setEndTime(availableSlot.getEndTime());
            roomAvailabilityRepository.save(roomAvailability);
        }

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

    public ResponseEntity<?> getRoomById(String roomId) throws ResourceNotFoundException {
        Room room = roomRepository.findById(roomId).orElseThrow(()
                -> new ResourceNotFoundException("Room not found : " + roomId));
        Response response = new Response();
        response.setStatus(HttpStatus.OK.value());
//        response.setError("");
//        response.setMessage("");
        response.setDateTime(LocalDateTime.now());
        response.setData(room);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<String> deleteRoom(String roomId) throws ResourceNotFoundException {
        Room room = roomRepository.findById(roomId).orElseThrow(()
                -> new ResourceNotFoundException("Room not found : " + roomId));

        roomRepository.deleteById(roomId);
        return new ResponseEntity<>("Room deleted successfully", HttpStatus.OK);
    }

    public ResponseEntity<?> searchRoom(String keyword) throws ResourceNotFoundException {
        ArrayList<Optional<Room>> rooms  = roomRepository.searchRoom(keyword);
        if(rooms.isEmpty())
            throw new ResourceNotFoundException("There are no rooms found : " + keyword);

        Response response = new Response();
        response.setStatus(HttpStatus.OK.value());
        response.setDateTime(LocalDateTime.now());
        response.setData(rooms);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // TODO: complete getRoomAmenities

//    public ResponseEntity<?> getRoomAmenities(String roomId) {
//        List<Optional<Amenity>> optionalAmenities = amenityRepository.findAllByRooms(roomId);
//        if(!optionalAmenities.isEmpty()) {
//            Response response = new Response();
//            response.setStatus(HttpStatus.OK.value());
//            response.setError("");
//            response.setMessage("");
//            response.setDateTime(LocalDateTime.now());
//            response.setData(optionalAmenities);
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        }
//        else
//            throw new ResourceNotFoundException("There are no room amenities");
//    }
}

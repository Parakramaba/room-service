package com.hifigod.roomservice.service;

import com.hifigod.roomservice.dto.Response;
import com.hifigod.roomservice.dto.RoomAvailabilityDto;
import com.hifigod.roomservice.dto.RoomDto;
import com.hifigod.roomservice.dto.RoomUpdateDto;
import com.hifigod.roomservice.exception.ResourceNotFoundException;
import com.hifigod.roomservice.exception.ValidationException;
import com.hifigod.roomservice.model.*;
import com.hifigod.roomservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

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
    private RoomAmenityRepository roomAmenityRepository;

    @Autowired
    private RoomAvailabilityRepository roomAvailabilityRepository;


    public ResponseEntity<?> createRoom(RoomDto roomDto) throws ResourceNotFoundException,
            ValidationException {
        if(roomDto.getName() == null || roomDto.getName().length() == 0)
            throw new ValidationException("Room name is required");

//        if(roomDto.getHourlyRate() < 0)
//            throw new ValidationException("Hourly rate should be zero or positive number");
//        if(roomDto.getNoOfGuest() < 0)
//            throw new ValidationException("Number of guest should be zero or positive number");

        User user = userRepository.findById(roomDto.getUserId()).
                orElseThrow(() -> new ResourceNotFoundException("User not found : " + roomDto.getUserId()));
        RoomType roomType = roomTypeRepository.findById(roomDto.getRoomTypeId()).
                orElseThrow(() -> new ResourceNotFoundException("Room type not found : " + roomDto.getRoomTypeId()));

        // TODO: validate the userName with userId uniqueness

//        Optional<Room> optionalRoom = roomRepository.findByNameAndUserId(roomDto.getName(), user.getUserId());
//
//        if(optionalRoom.isPresent())
//            throw new ValidationException("You have a room with the same name.Please give different room name");

        // Save basic room details
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
//        roomRepository.save(room);

        // Save room amenities
        ArrayList<RoomAmenity> roomAmenities = new ArrayList<>();
        for (String amenityId:
             roomDto.getAmenitiesIdList()) {
            Amenity amenity = amenityRepository.findById(amenityId).orElseThrow(()
                    -> new ResourceNotFoundException("Amenity not found : " + amenityId));
            UUID roomAmenityId = UUID.randomUUID();
            RoomAmenity roomAmenity = new RoomAmenity();
            roomAmenity.setId(roomAmenityId.toString());
            roomAmenity.setRoom(room);
            roomAmenity.setAmenity(amenity);
            roomAmenities.add(roomAmenity);

        }
        roomRepository.save(room);
        roomAmenityRepository.saveAll(roomAmenities);

//        List<Amenity> amenitiesList = new ArrayList<>();
//        for (String amenityId:
//                roomDto.getAmenitiesIdList()) {
//            Amenity amenity = amenityRepository.findById(amenityId).orElseThrow(()
//                    -> new ResourceNotFoundException("Amenity not found : " + amenityId));
//            amenitiesList.add(amenity);
//        }
//        room.setAmenities(amenitiesList);

        // Save room availabilities
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
        List<Room> rooms = roomRepository.findAllByDeletedFalse();
        if(rooms.isEmpty())
            throw new ResourceNotFoundException("There are no rooms found");
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    public ResponseEntity<?> getRoomById(String roomId) throws ResourceNotFoundException {
        Room room = roomRepository.findByIdAndDeletedFalse(roomId).orElseThrow(()
                -> new ResourceNotFoundException("Room not found : " + roomId));
        Response response = new Response();
        response.setStatus(HttpStatus.OK.value());
//        response.setError("");
//        response.setMessage("");
        response.setDateTime(LocalDateTime.now());
        response.setData(room);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<String> updateRoom(String roomId,RoomUpdateDto roomUpdateDto) throws ResourceNotFoundException {
        Room room = roomRepository.findById(roomId).orElseThrow(()
                -> new ResourceNotFoundException("Room not found : " + roomId));

        if(roomUpdateDto.getName() != null && roomUpdateDto.getName().length() > 0)
            room.setName(roomUpdateDto.getName());
        if(roomUpdateDto.getDescription() != null && roomUpdateDto.getDescription().length() > 0)
            room.setDescription(roomUpdateDto.getDescription());
        if(roomUpdateDto.getNoOfGuest() != null)
            room.setNoOfGuest(roomUpdateDto.getNoOfGuest());
        if(roomUpdateDto.getHourlyRate() != null)
            room.setHourlyRate(roomUpdateDto.getHourlyRate());
        if(roomUpdateDto.getLatitude() != null)
            room.setLatitude(roomUpdateDto.getLatitude());
        if(roomUpdateDto.getLongitude() != null)
            room.setLongitude(roomUpdateDto.getLongitude());
        if(roomUpdateDto.getCountry() != null && roomUpdateDto.getCountry().length() > 0)
            room.setCountry(roomUpdateDto.getCountry());
        if(roomUpdateDto.getStreetAddress() != null && roomUpdateDto.getStreetAddress().length() > 0)
            room.setStreetAddress(roomUpdateDto.getStreetAddress());
        if(roomUpdateDto.getApartmentNo() != null && roomUpdateDto.getApartmentNo().length() > 0)
            room.setApartmentNo(roomUpdateDto.getApartmentNo());
        if(roomUpdateDto.getCity() != null && roomUpdateDto.getCity().length() > 0)
            room.setCity(roomUpdateDto.getCity());
        if(roomUpdateDto.getPostCode() != null && roomUpdateDto.getPostCode().length() > 0)
            room.setPostCode(roomUpdateDto.getPostCode());
        if(roomUpdateDto.getSetupCost() != null)
            room.setSetupCost(roomUpdateDto.getSetupCost());

        List<RoomAmenity> roomAmenities = new ArrayList<>();
        if(!roomUpdateDto.getAmenitiesIdList().isEmpty()) {
            for (String amenityId:
                 roomUpdateDto.getAmenitiesIdList()) {
                Amenity amenity = amenityRepository.findById(amenityId).orElseThrow(()
                        -> new ResourceNotFoundException("Amenity not found : " + amenityId));
                if(roomAmenityRepository.findByRoomIdAndAmenityId(roomId, amenityId).isEmpty()) {
                    UUID roomAmenityId = UUID.randomUUID();
                    RoomAmenity roomAmenity = new RoomAmenity();
                    roomAmenity.setId(roomAmenityId.toString());
                    roomAmenity.setRoom(room);
                    roomAmenity.setAmenity(amenity);
                    roomAmenities.add(roomAmenity);
                }
            }
        }

        // TODO: update availabilities

        roomRepository.save(room);
        roomAmenityRepository.saveAll(roomAmenities);
        return new ResponseEntity<>("Room details updated successfully", HttpStatus.OK);

    }

    public ResponseEntity<String> deleteRoom(String roomId) throws ResourceNotFoundException {
        Room room = roomRepository.findByIdAndDeletedFalse(roomId).orElseThrow(()
                -> new ResourceNotFoundException("Room not found : " + roomId));

        roomRepository.deleteById(roomId);
        return new ResponseEntity<>("Room deleted successfully", HttpStatus.OK);
    }

    public ResponseEntity<?> getRoomsByUser(String userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new ResourceNotFoundException("User not found : " + userId));
        List<Room> userRooms = roomRepository.findAllByUserIdAndDeletedFalse(userId);
        if(userRooms.isEmpty())
            throw new ResourceNotFoundException("There are no rooms for the user : " + userId);
        return new ResponseEntity<>(userRooms, HttpStatus.OK);
    }

    public ResponseEntity<?> searchRoom(String keyword) throws ResourceNotFoundException {
        List<Room> rooms  = roomRepository.searchRoom(keyword);
        if(rooms.isEmpty())
            throw new ResourceNotFoundException("There are no rooms found : " + keyword);

        Response response = new Response();
        response.setStatus(HttpStatus.OK.value());
        response.setDateTime(LocalDateTime.now());
        response.setData(rooms);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    public ResponseEntity<?> getRoomAmenities(String roomId) {
//        Room room = roomRepository.findByIdAndDeletedFalse(roomId).orElseThrow(()
//                -> new ResourceNotFoundException("Room not found : " + roomId));
//        List<Optional<?>> optionalAmenities = roomAmenityRepository.findByRoomId(roomId);
//        if(!optionalAmenities.isEmpty()) {
//            Response response = new Response();
//            response.setStatus(HttpStatus.OK.value());
//            response.setDateTime(LocalDateTime.now());
//            response.setData(optionalAmenities);
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        }
//        else
//            throw new ResourceNotFoundException("There are no room amenities");
//    }
}

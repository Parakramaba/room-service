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

/**
 * This Service class implements the business logic of the endpoints which are provided in the RoomController.
 * */
@Service("RoomService")
public class RoomService {

    // INJECT REPOSITORY OBJECT DEPENDENCIES
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
    // / INJECT REPOSITORY OBJECT DEPENDENCIES

    // INJECT SERVICE OBJECT DEPENDENCIES
    @Autowired
    private UtilService utilService;


    public ResponseEntity<?> createRoom(final RoomDto roomDto) throws ResourceNotFoundException,
            ValidationException {
        if (roomDto.getName() == null || roomDto.getName().length() == 0) {
            throw new ValidationException("Room name is required");
        }

//        if(roomDto.getHourlyRate() < 0)
//            throw new ValidationException("Hourly rate should be zero or positive number");
//        if(roomDto.getNoOfGuest() < 0)
//            throw new ValidationException("Number of guest should be zero or positive number");

        User user = utilService.checkUserById(roomDto.getUserId());
        RoomType roomType = utilService.checkRoomTypeById(roomDto.getRoomTypeId());

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

        // Save room amenities
        ArrayList<RoomAmenity> roomAmenities = new ArrayList<>();
        for (String amenityId
                : roomDto.getAmenitiesIdList()) {
            Amenity amenity = utilService.checkAmenityById(amenityId);
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
//            Amenity amenity = utilService.checkAmenityById(amenityId);
//            amenitiesList.add(amenity);
//        }
//        room.setAmenities(amenitiesList);

        // TODO: handle the room availabilities when verify
        // Save room availabilities
        for (RoomAvailabilityDto availableSlot
                : roomDto.getRoomAvailabilities()) {
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
        response.setData(room.getId());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This returns a ResponseEntity with the List of all available rooms.
     * @return List of all rooms
     */
    public ResponseEntity<?> getAllRooms() {
        List<Room> rooms = roomRepository.findAllByDeletedFalse();
        if (rooms.isEmpty()) {
            return new ResponseEntity<>("There are no rooms found", HttpStatus.OK);
        }

        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    /**
     * This returns a ResponseEntity with the details of a room. The roomId must be valid,
     * otherwise an exception will be thrown
     * @param roomId ID of the room, not null
     * @return Details of the room, not null
     * @throws ResourceNotFoundException If the roomId is invalid
     */
    public ResponseEntity<?> getRoomById(final String roomId) throws ResourceNotFoundException {
        Room room = utilService.checkRoomById(roomId);
        Response response = new Response();
        response.setStatus(HttpStatus.OK.value());
//        response.setError("");
//        response.setMessage("");
        response.setDateTime(LocalDateTime.now());
        response.setData(room);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This returns a ResponseEntity with the List of rooms of the requested room type. The roomType must be valid,
     * otherwise an exception will be thrown
     * @param roomTypeId ID of the room type, not null
     * @return List of rooms of requested room type, not null
     * @throws ResourceNotFoundException If the roomType is invalid
     */
    public ResponseEntity<?> getRoomsByType(final String roomTypeId) throws ResourceNotFoundException {
        RoomType roomType = utilService.checkRoomTypeById(roomTypeId);
        List<Room> rooms = roomRepository.findAllByRoomTypeIdAndDeletedFalse(roomTypeId);
        if (rooms.isEmpty()) {
            return new ResponseEntity<>("There are no rooms found for the type : " + roomTypeId, HttpStatus.OK);
        }

        Response response = new Response();
        response.setStatus(HttpStatus.OK.value());
        response.setDateTime(LocalDateTime.now());
        response.setData(rooms);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<String> updateRoom(final String roomId, final RoomUpdateDto roomUpdateDto)
            throws ResourceNotFoundException {
        Room room = utilService.checkRoomById(roomId);

        if (roomUpdateDto.getName() != null && roomUpdateDto.getName().length() > 0) {
            room.setName(roomUpdateDto.getName());
        }
        if (roomUpdateDto.getDescription() != null && roomUpdateDto.getDescription().length() > 0) {
            room.setDescription(roomUpdateDto.getDescription());
        }
        if (roomUpdateDto.getNoOfGuest() != null) {
            room.setNoOfGuest(roomUpdateDto.getNoOfGuest());
        }
        if (roomUpdateDto.getHourlyRate() != null) {
            room.setHourlyRate(roomUpdateDto.getHourlyRate());
        }
        if (roomUpdateDto.getLatitude() != null) {
            room.setLatitude(roomUpdateDto.getLatitude());
        }
        if (roomUpdateDto.getLongitude() != null) {
            room.setLongitude(roomUpdateDto.getLongitude());
        }
        if (roomUpdateDto.getCountry() != null && roomUpdateDto.getCountry().length() > 0) {
            room.setCountry(roomUpdateDto.getCountry());
        }
        if (roomUpdateDto.getStreetAddress() != null && roomUpdateDto.getStreetAddress().length() > 0) {
            room.setStreetAddress(roomUpdateDto.getStreetAddress());
        }
        if (roomUpdateDto.getApartmentNo() != null && roomUpdateDto.getApartmentNo().length() > 0) {
            room.setApartmentNo(roomUpdateDto.getApartmentNo());
        }
        if (roomUpdateDto.getCity() != null && roomUpdateDto.getCity().length() > 0) {
            room.setCity(roomUpdateDto.getCity());
        }
        if (roomUpdateDto.getPostCode() != null && roomUpdateDto.getPostCode().length() > 0) {
            room.setPostCode(roomUpdateDto.getPostCode());
        }
        if (roomUpdateDto.getSetupCost() != null) {
            room.setSetupCost(roomUpdateDto.getSetupCost());
        }

        List<RoomAmenity> roomAmenities = new ArrayList<>();
        if (!roomUpdateDto.getAmenitiesIdList().isEmpty()) {
            for (String amenityId
                    : roomUpdateDto.getAmenitiesIdList()) {
                Amenity amenity = utilService.checkAmenityById(amenityId);
                if (roomAmenityRepository.findByRoomIdAndAmenityId(roomId, amenityId).isEmpty()) {
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

    /**
     * This will delete an existing room.
     * @param roomId ID of the room, not null
     * @return Success message of deletion, not null
     * @throws ResourceNotFoundException If the roomId is invalid
     */
    public ResponseEntity<String> deleteRoom(final String roomId) throws ResourceNotFoundException {
        Room room = utilService.checkRoomById(roomId);

        roomRepository.deleteById(roomId);
        return new ResponseEntity<>("Room deleted successfully", HttpStatus.OK);
    }

    /**
     * This returns ResponseEntity with the List of user rooms. The userId must be valid,
     * otherwise an exception will be thrown
     * @param userId ID of the user, not null
     * @return List of user rooms, not null
     * @throws ResourceNotFoundException If the userId is invalid
     */
    public ResponseEntity<?> getRoomsByUser(final String userId) throws ResourceNotFoundException {
        User user = utilService.checkUserById(userId);
        List<Room> userRooms = roomRepository.findAllByUserIdAndDeletedFalse(userId);
        if (userRooms.isEmpty()) {
            return new ResponseEntity<>("There are no rooms found for the user : " + userId, HttpStatus.OK);
        }

        return new ResponseEntity<>(userRooms, HttpStatus.OK);
    }

    /**
     * This returns ResponseEntity with the List of rooms that match with the search keyword.
     * @param keyword Search keyword, not null
     * @return List of rooms that match with the keyword, not null
     */
    public ResponseEntity<?> searchRoom(final String keyword) {
        List<Room> rooms  = roomRepository.searchRoom(keyword);
        if (rooms.isEmpty()) {
            return new ResponseEntity<>("There are no rooms found for the keyword : " + keyword, HttpStatus.OK);
        }

        Response response = new Response();
        response.setStatus(HttpStatus.OK.value());
        response.setDateTime(LocalDateTime.now());
        response.setData(rooms);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    public ResponseEntity<?> getRoomAmenities(String roomId) {
//            Room room = utilService.checkRoomById(roomId);
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

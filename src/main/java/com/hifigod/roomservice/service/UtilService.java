package com.hifigod.roomservice.service;

import com.hifigod.roomservice.exception.ErrorMessages;
import com.hifigod.roomservice.exception.ResourceNotFoundException;
import com.hifigod.roomservice.model.Amenity;
import com.hifigod.roomservice.model.Room;
import com.hifigod.roomservice.model.RoomType;
import com.hifigod.roomservice.model.User;
import com.hifigod.roomservice.repository.AmenityRepository;
import com.hifigod.roomservice.repository.RoomRepository;
import com.hifigod.roomservice.repository.RoomTypeRepository;
import com.hifigod.roomservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This Class implements all the common methods that needed in the other service classes.
 */
@Service("UtilService")
public class UtilService {

    // INJECT REPOSITORY OBJECT DEPENDENCIES
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private AmenityRepository amenityRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;
    // / INJECT REPOSITORY OBJECT DEPENDENCIES


    // CHECK THE AVAILABILITY OF A SINGLE RESOURCE
    /**
     * @param userId ID of the user, not null
     * @return User, not null
     * @throws ResourceNotFoundException If the userId is invalid
     */
    public User checkUserById(String userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND_MSG + userId));
        return user;
    }

    /**
     * @param roomId ID of the room, not null
     * @return Room, not null
     * @throws ResourceNotFoundException If the roomId is invalid
     */
    public Room checkRoomById(String roomId) throws ResourceNotFoundException {
        Room room = roomRepository.findByIdAndDeletedFalse(roomId).orElseThrow(()
                -> new ResourceNotFoundException(ErrorMessages.ROOM_NOT_FOUND_MSG + roomId));
        return room;
    }

    /**
     * @param roomTypeId ID of the room type, not null
     * @return RoomType, not null
     * @throws ResourceNotFoundException If the roomTypeId is invalid
     */
    public RoomType checkRoomTypeById(String roomTypeId) throws ResourceNotFoundException {
        RoomType roomType = roomTypeRepository.findById(roomTypeId).orElseThrow(()
                -> new ResourceNotFoundException(ErrorMessages.ROOM_TYPE_NOT_FOUND_MSG + roomTypeId));
        return roomType;
    }

    /**
     * @param amenityId ID of the amenity, not null
     * @return Amenity, not null
     * @throws ResourceNotFoundException If the amenityId is invalid
     */
    public Amenity checkAmenityById(String amenityId) throws ResourceNotFoundException {
        Amenity amenity = amenityRepository.findById(amenityId).orElseThrow(()
                -> new ResourceNotFoundException(ErrorMessages.AMENITY_NOT_FOUND_MSG + amenityId));
        return amenity;
    }
    // / CHECK THE AVAILABILITY OF A SINGLE RESOURCE

}

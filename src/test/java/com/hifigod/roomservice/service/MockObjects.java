package com.hifigod.roomservice.service;

import com.hifigod.roomservice.dto.RoomDto;
import com.hifigod.roomservice.dto.RoomUpdateDto;
import com.hifigod.roomservice.model.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Data
public class MockObjects {

    // User
    User user1 = new User(UUID.randomUUID().toString(), "User-1", Collections.emptyList(), LocalDateTime.now());
    User user2 = new User(UUID.randomUUID().toString(),"User-2", Collections.emptyList(), LocalDateTime.now());

    // RoomType
    RoomType roomType1 = new RoomType(UUID.randomUUID().toString(),"Listening",
            Collections.emptyList(), LocalDateTime.now());
    RoomType roomType2 = new RoomType(UUID.randomUUID().toString(),"Recording",
            Collections.emptyList(), LocalDateTime.now());

    // Amenity
    Amenity amenity1 = new Amenity(UUID.randomUUID().toString(), "Amenity-1",
            Collections.emptyList(), LocalDateTime.now());
    Amenity amenity2 = new Amenity(UUID.randomUUID().toString(), "Amenity-2",
            Collections.emptyList(), LocalDateTime.now());

    // RoomAmenity
    RoomAmenity roomAmenity1 = new RoomAmenity(UUID.randomUUID().toString(),  null,
            amenity1, false, LocalDateTime.now());
    RoomAmenity roomAmenity2 = new RoomAmenity(UUID.randomUUID().toString(), null,
            amenity2,false,  LocalDateTime.now());

    // List of RoomAmenities
    List<RoomAmenity> roomAmenities = new ArrayList<>(){{ add(roomAmenity1); add(roomAmenity2); }};

    // List of AmenitiesID
    List<String> amenitiesIdList = new ArrayList<>(){{ add("123"); add("456"); }};

    // RoomDto
    RoomDto roomDto = new RoomDto("Room-1", "Description of Room-1", "111",
            "2", 20, 40, 12.784562, 128.784523,
            "Sri Lanka", "Street Address 1", "AP:123C", "Kandy",
            "23456", 20000L, Collections.emptyList(), Collections.emptyList());

    RoomDto roomDtoWithAmenities = new RoomDto("Room with Amenities", "Description of Room with Amenities",
            "111", "1", 15, 45, 12.872123, 134.452981,
            "Sri Lanka", "Street Address-5", "AP:123RA", "Kandy",
            "34500", 20000L, amenitiesIdList , Collections.emptyList());

    RoomUpdateDto roomUpdateDto = new RoomUpdateDto("Updating Room", "Description of Updating Room",
            25, 30, 65.333333, 130.666666, "Sri Lanka",
            "Street Address-6", "AP:123U", "Polonnaruwa", "12000",
            15000L, Collections.emptyList(), Collections.emptyList());

    // Room
    Room deletedRoom1 = new Room(UUID.randomUUID().toString(), "Deleted Room-1", "Description of Deleted Room-1", user1, roomType1,
            15, 35, 12.452343, 124.124981, "Sri Lanka", "Street Address-1", "AP:123",
            "Matara", "81002", 12000L, "Not verified",true, true, null, null,
            LocalDateTime.now(), LocalDateTime.of(2022,6,3,15,40,0), Collections.emptyList(), Collections.emptyList());

    Room deletedRoom2 = new Room(UUID.randomUUID().toString(), "Deleted Room-2", "Description of Deleted Room-2", user2, roomType2,
            20, 50, 24.452143, 143.122381,"Sri Lanka", "Street Address-2", "AP:456",
            "Colombo", "81045", 15000L, "Not verified",true, true, null, null,
            LocalDateTime.now(), LocalDateTime.of(2022,6,3,15,40,0), Collections.emptyList(), Collections.emptyList());

    Room availableRoom1 = new Room(UUID.randomUUID().toString(), "Available Room-1", "Description of Available Room-1",
            user1, roomType2, 15, 40, 29.4521123, 173.382381,
            "Sri Lanka", "Street Address-3", "AP:123A", "Galle",
            "72340", 18000L, "Not verified", true, false, null,
            null, LocalDateTime.now(), null,Collections.emptyList(), Collections.emptyList());

    Room availableRoom2 = new Room(UUID.randomUUID().toString(), "Available Room-2", "Description of Available Room-2",
            user2, roomType2, 10, 35, 4.124523, 113.928381,
            "Sri Lanka", "Street Address-4", "AP:456A", "Kurunegala",
            "64000", 15000L, "Not verified", true, false, null,
            null, LocalDateTime.now(), null, Collections.emptyList(), Collections.emptyList());

    Room roomWithAmenities = new Room(UUID.randomUUID().toString(), "Room with Amenities", "Description of Room with Amenities",
            user1, roomType1, 15, 45, 12.872123, 134.452981,
            "Sri Lanka", "Street Address-5", "AP:123RA", "Kandy",
            "34500", 20000L, "Not verified",true, false, null,
            null, LocalDateTime.now(), null, roomAmenities , Collections.emptyList());
}

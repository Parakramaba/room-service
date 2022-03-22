package com.hifigod.roomservice.service;

import com.hifigod.roomservice.model.Amenity;
import com.hifigod.roomservice.model.Room;
import com.hifigod.roomservice.model.RoomType;
import com.hifigod.roomservice.model.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@Data
public class MockObjects {

    // User
    User user1 = new User(UUID.randomUUID().toString(), Collections.emptyList(), LocalDateTime.now());
    User user2 = new User(UUID.randomUUID().toString(), Collections.emptyList(), LocalDateTime.now());

    // RoomType
    RoomType roomType1 = new RoomType(UUID.randomUUID().toString(),"Room type-1",
            Collections.emptyList(), LocalDateTime.now());
    RoomType roomType2 = new RoomType(UUID.randomUUID().toString(),"Room type-2",
            Collections.emptyList(), LocalDateTime.now());

    // Room
    Room room1 = new Room(UUID.randomUUID().toString(), "Room-1", "Description for Room-1",
            user1, roomType1, 15, 35, 12.452343, 124.124981,
            "Sri Lanka", "Street Address-1", "AP:123", "Matara",
            "81002", 12000L, "Not verified", null, null,
            LocalDateTime.now(), true, Collections.emptyList(), Collections.emptyList());

    Room room2 = new Room(UUID.randomUUID().toString(), "Room-2", "Description for Room-2",
            user2, roomType2, 20, 50, 24.452143, 143.122381,
            "Sri Lanka", "Street Address-2", "AP:456", "Colombo",
            "81045", 15000L, "Not verified", null, null,
            LocalDateTime.now(), true, Collections.emptyList(), Collections.emptyList());

    // Amenity
    Amenity amenity1 = new Amenity(UUID.randomUUID().toString(), "Amenity-1",
            Collections.emptyList(), LocalDateTime.now());
    Amenity amenity2 = new Amenity(UUID.randomUUID().toString(), "Amenity-2",
            Collections.emptyList(), LocalDateTime.now());
}

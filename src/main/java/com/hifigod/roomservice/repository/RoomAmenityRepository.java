package com.hifigod.roomservice.repository;

import com.hifigod.roomservice.model.RoomAmenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoomAmenityRepository extends JpaRepository<RoomAmenity, String> {

//    @Query(value = "SELECT amenity_id FROM room_amenity rAmenity WHERE rAmenity.room_id=?1", nativeQuery = true)
//    List<Optional<?>> findByRoomId(String roomId);
}

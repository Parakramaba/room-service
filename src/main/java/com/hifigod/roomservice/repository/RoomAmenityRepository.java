package com.hifigod.roomservice.repository;

import com.hifigod.roomservice.model.RoomAmenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoomAmenityRepository extends JpaRepository<RoomAmenity, String> {

//    @Query(value = "SELECT amenity_id FROM room_amenity rAmenity WHERE rAmenity.room_id=?1", nativeQuery = true)
//    List<Optional<?>> findByRoomId(String roomId);

    /**
     * This returns a RoomAmenity if it already exists
     * @param roomId ID of the room, not null
     * @param amenityId ID of the amenity, not null
     * @return RoomAmenity
     */
    Optional<RoomAmenity> findByRoomIdAndAmenityId(String roomId, String amenityId);
}

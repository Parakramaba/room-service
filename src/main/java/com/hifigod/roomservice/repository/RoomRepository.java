package com.hifigod.roomservice.repository;

import com.hifigod.roomservice.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, String> {

    /**
     * @return List of all available rooms, not null
     */
    List<Room> findAllByIsDeletedFalse();

    /**
     * This returns a room if it is available.
     * @param id ID of the room, not null
     * @return Details of the available room
     */
    Optional<Room> findByIdAndIsDeletedFalse(String id);

    /**
     * @return List of all deleted rooms, not null
     */
    List<Room> findAllByIsDeletedTrue();

    /**
     * @param roomType Type of the room, not null
     * @return List of all available rooms of a certain room type, not null
     */
    List<Room> findAllByRoomTypeIdAndIsDeletedFalse(String roomType);

//    Optional<Room> findByIdAndDeletedTrue(String id);

//    @Query(value = "SELECT * FROM room where user_id = ?1 AND deleted=false", nativeQuery = true)

    /**
     * @param userId ID of the user, not null
     * @return List of all available rooms of a user, not null
     */
    List<Room> findAllByUserIdAndIsDeletedFalse(String userId);

    /**
     *
     * @param keyword Search keyword, not null
     * @return List of all the rooms that match with the search keyword, not null
     */
    @Query(value = "SELECT * FROM room WHERE MATCH(name, description, country, city) AGAINST (?1) AND is_deleted=false",
            nativeQuery = true)
    List<Room> searchRoom(String keyword);
}

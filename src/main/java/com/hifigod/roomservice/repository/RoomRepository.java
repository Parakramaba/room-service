package com.hifigod.roomservice.repository;

import com.hifigod.roomservice.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, String> {

    List<Room> findAllByDeletedFalse();

    Optional<Room> findByIdAndDeletedFalse(String id);

    List<Room> findAllByDeletedTrue();

//    Optional<Room> findByIdAndDeletedTrue(String id);

//    @Query(value = "SELECT * FROM room where user_id = ?1 AND deleted=false", nativeQuery = true)
    List<Room> findAllByUserIdAndDeletedFalse(String userId);

    @Query(value = "SELECT * FROM room WHERE MATCH(name, description, country, city) AGAINST (?1) AND deleted=false",
            nativeQuery = true)
    List<Room> searchRoom(String keyword);
}

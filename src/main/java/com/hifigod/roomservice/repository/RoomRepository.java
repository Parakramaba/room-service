package com.hifigod.roomservice.repository;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import com.hifigod.roomservice.model.Amenity;
import com.hifigod.roomservice.model.Room;
import com.hifigod.roomservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, String> {

    ArrayList<Optional<Room>> findAllByDeletedFalse();

    Optional<Room> findByIdAndDeletedFalse(String id);

    ArrayList<Optional<Room>> findAllByDeletedTrue();

//    Optional<Room> findByIdAndDeletedTrue(String id);


    @Query(value = "SELECT * FROM room WHERE MATCH(name, description, country, city) AGAINST (?1) AND deleted=false",
            nativeQuery = true)
    ArrayList<Optional<Room>> searchRoom(String keyword);
}

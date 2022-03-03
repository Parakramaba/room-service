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
//    @Query(value = "DELETE FROM")
//    void deleteAllById(String roomId);


    @Query(value = "SELECT * FROM room WHERE MATCH(name, description, country, city) AGAINST (?1) AND deleted=false",
            nativeQuery = true)
    ArrayList<Optional<Room>> searchRoom(String keyword);
}

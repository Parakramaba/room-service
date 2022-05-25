package com.hifigod.roomservice.repository;

import com.hifigod.roomservice.model.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AmenityRepository extends JpaRepository<Amenity, String> {
//    List<Optional<Amenity>> findAllByRoomId(String roomId);
}

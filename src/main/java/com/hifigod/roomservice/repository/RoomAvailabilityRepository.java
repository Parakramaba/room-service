package com.hifigod.roomservice.repository;

import com.hifigod.roomservice.model.RoomAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomAvailabilityRepository extends JpaRepository<RoomAvailability, String> {
}

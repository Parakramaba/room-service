package com.hifigod.roomservice.repository;

import com.hifigod.roomservice.model.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenityRepository extends JpaRepository<Amenity, Integer> {
}

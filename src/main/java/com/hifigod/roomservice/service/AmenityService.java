package com.hifigod.roomservice.service;

import com.hifigod.roomservice.exception.ResourceNotFoundException;
import com.hifigod.roomservice.model.Amenity;
import com.hifigod.roomservice.repository.AmenityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This Service class implements the business logic of the endpoints which are provided in the AmenityController.
 * */
@Service("AmenityService")
public class AmenityService {

    // INJECT REPOSITORY OBJECT DEPENDENCIES
    @Autowired
    private AmenityRepository amenityRepository;

    /**
     * This returns a ResponseEntity with the List of all amenities.
     * @return List of all amenities
     */
    public ResponseEntity<?> getAllAmenities() {
        List<Amenity> amenities = amenityRepository.findAll();
        if (amenities.isEmpty()) {
            return new ResponseEntity<>("There are no amenities found", HttpStatus.OK);
        }

        return new ResponseEntity<>(amenities, HttpStatus.OK);
    }

}

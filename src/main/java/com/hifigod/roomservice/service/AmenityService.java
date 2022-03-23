package com.hifigod.roomservice.service;

import com.hifigod.roomservice.exception.ResourceNotFoundException;
import com.hifigod.roomservice.model.Amenity;
import com.hifigod.roomservice.repository.AmenityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("AmenityService")
public class AmenityService {

    @Autowired
    private AmenityRepository amenityRepository;

    public ResponseEntity<?> getAllAmenities() throws ResourceNotFoundException {
        List<Amenity> amenities = amenityRepository.findAll();
        if(amenities.isEmpty())
            throw new ResourceNotFoundException("There are no amenities found");
        return new ResponseEntity<>(amenities, HttpStatus.OK);
    }

}

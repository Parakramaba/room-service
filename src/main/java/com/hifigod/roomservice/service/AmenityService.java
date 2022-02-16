package com.hifigod.roomservice.service;

import com.hifigod.roomservice.exception.ResourceNotFoundException;
import com.hifigod.roomservice.repository.AmenityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service("AmenityService")
public class AmenityService {

    @Autowired
    private AmenityRepository amenityRepository;

    public ResponseEntity<?> getAllAmenities() throws ResourceNotFoundException {
        if(amenityRepository.findAll().isEmpty())
            throw new ResourceNotFoundException("There are no amenities found");
        return new ResponseEntity<>(amenityRepository.findAll(), HttpStatus.OK);
    }

}

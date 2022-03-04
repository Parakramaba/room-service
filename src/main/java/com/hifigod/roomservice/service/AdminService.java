package com.hifigod.roomservice.service;

import com.hifigod.roomservice.exception.ResourceNotFoundException;
import com.hifigod.roomservice.model.Room;
import com.hifigod.roomservice.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("AdminService")
public class AdminService {

    @Autowired
    private RoomRepository roomRepository;

    public ResponseEntity<?> getAllDeletedRooms() throws ResourceNotFoundException {
        ArrayList<Optional<Room>> rooms = roomRepository.findAllByDeletedTrue();
        if(rooms.isEmpty())
            throw new ResourceNotFoundException("There are no deleted rooms found");
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

}

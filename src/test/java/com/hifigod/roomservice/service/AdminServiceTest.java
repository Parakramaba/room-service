package com.hifigod.roomservice.service;

import com.hifigod.roomservice.dto.AmenityDto;
import com.hifigod.roomservice.exception.ResourceNotFoundException;
import com.hifigod.roomservice.exception.ValidationException;
import com.hifigod.roomservice.model.Amenity;
import com.hifigod.roomservice.model.Room;
import com.hifigod.roomservice.repository.AmenityRepository;
import com.hifigod.roomservice.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @MockBean
    private AmenityRepository amenityRepository;

    @MockBean
    private RoomRepository roomRepository;

    // TEST ROOM DATA
    @Test
    void getAllDeletedRooms_Success() {
        Room room1 = new MockObjects().getDeletedRoom1();
        Room room2 = new MockObjects().getDeletedRoom2();

        when(roomRepository.findAllByDeletedTrue())
                .thenReturn(Stream.of(room1, room2).collect(Collectors.toList()));

        assertEquals(HttpStatus.OK, adminService.getAllDeletedRooms().getStatusCode(),
                "Should have Status code '200 OK'");
        verify(roomRepository, times(1)).findAllByDeletedTrue();
    }

    @Test
    void getAllDeletedRooms_WhenNoDeletedRoomsFound_Success() {
        when(roomRepository.findAllByDeletedTrue())
                .thenReturn(Collections.emptyList());

        assertEquals(HttpStatus.OK, adminService.getAllDeletedRooms().getStatusCode(),
                "Should have Status code '200 OK'");
        verify(roomRepository, times(1)).findAllByDeletedTrue();

    }
    // / TEST ROOM DATA

    // TEST AMENITY DATA
    @Test
    void createAmenity_Success() {
        Amenity amenity = new MockObjects().getAmenity1();
        when(amenityRepository.save(any(Amenity.class)))
                .thenReturn(amenity);

        assertEquals(HttpStatus.OK, adminService.createAmenity(new AmenityDto("Amenity-1")).getStatusCode(),
                "Should have Status code '200 OK'");
        verify(amenityRepository, times(1)).save(any(Amenity.class));
    }

    @Test
    void createAmenity_WhenMandatoryFieldsAreMissing_ThrowValidationException() {
        Amenity amenity = new MockObjects().getAmenity1();
        when(amenityRepository.save(any(Amenity.class)))
                .thenReturn(amenity);

//        String expectedMessage = "Amenity name is required";
//        Exception exception = assertThrows(ValidationException.class, ()->adminService.createAmenity(new AmenityDto()));
//        assertEquals(expectedMessage, exception.getMessage());
        assertThrows(ValidationException.class,()->adminService.createAmenity(new AmenityDto()),
                "Should throw ValidationException");
        verify(amenityRepository, never()).save(amenity);
    }
    // / TEST AMENITY DATA
}
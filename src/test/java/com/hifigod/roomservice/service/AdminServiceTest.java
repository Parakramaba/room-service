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

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @MockBean
    private AmenityRepository amenityRepository;

    @MockBean
    private RoomRepository roomRepository;

    // TEST ROOM DATA
    @Test
    public void getAllDeletedRooms_Success() {
        Room room1 = new MockObjects().getRoom1();
        Room room2 = new MockObjects().getRoom2();
        when(roomRepository.findAllByDeletedTrue()).thenReturn(Stream
                .of(room1, room2).collect(Collectors.toList()));
//        assertEquals(new ArrayList<>(){{ add(room1); add(room2); }}, adminService.getAllDeletedRooms().getBody(),
//                "Should return list of all deleted rooms");
        assertEquals(HttpStatus.OK, adminService.getAllDeletedRooms().getStatusCode(),
                "Should return Status code '200 OK'");
        verify(roomRepository, times(1)).findAllByDeletedTrue();
    }

    @Test
    public void getAllDeletedRooms_ThrowResourceNotFoundException_WhenNoDeletedRoomsFound () {
        when(roomRepository.findAllByDeletedTrue()).thenReturn(Collections.emptyList());
        assertThrows(ResourceNotFoundException.class, () -> adminService.getAllDeletedRooms(),
                "Should throw ResourceNotFoundException");
        verify(roomRepository, times(1)).findAllByDeletedTrue();

    }
    // / TEST ROOM DATA

    // TEST AMENITY DATA
    // TODO: handle create when using DTOs
//    @Test
//    public void createAmenity_Success() {
//        Amenity amenity = new MockObjects().getAmenity1();
//        when(amenityRepository.save(amenity)).thenReturn(amenity);
//        ResponseEntity<?> responseEntity = adminService.createAmenity(new AmenityDto("Amenity-1"));
//
////        Amenity amenity1 = adminService.createAmenity(new AmenityDto("Amenity-2"));
////        Response response = (Response) responseEntity.getBody();
////        assertEquals(36, response.getData().toString().length(), "Should return 36");
////        assertEquals(amenity, amenity1);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
////        verify(amenityRepository, times(1)).save(amenity);
//    }

    @Test
    public void createAmenity_ThrowValidationException_WhenMandatoryFieldsAreMissing() {
        Amenity amenity = new MockObjects().getAmenity1();
        when(amenityRepository.save(amenity)).thenReturn(amenity);
//        String expectedMessage = "Amenity name is required";
//        Exception exception = assertThrows(ValidationException.class, ()->adminService.createAmenity(new AmenityDto()));
//        assertEquals(expectedMessage, exception.getMessage());
        assertThrows(ValidationException.class,()->adminService.createAmenity(new AmenityDto()),
                "Should throw ValidationException");
        verify(amenityRepository, times(0)).save(amenity);
    }
    // / TEST AMENITY DATA
}
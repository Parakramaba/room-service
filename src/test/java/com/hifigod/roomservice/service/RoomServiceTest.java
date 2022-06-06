package com.hifigod.roomservice.service;

import com.hifigod.roomservice.dto.RoomDto;
import com.hifigod.roomservice.dto.RoomUpdateDto;
import com.hifigod.roomservice.exception.ResourceNotFoundException;
import com.hifigod.roomservice.model.*;
import com.hifigod.roomservice.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private AmenityRepository amenityRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoomTypeRepository roomTypeRepository;

    @MockBean
    private RoomAmenityRepository roomAmenityRepository;

    /**
     * # when(repositoryMockBean.JpaMethodName()).thenReturn(mockedObjectToReturn)
     *
     * # When autowiredServiceBean call to the intended method in the service layer
     * and within that method it calls to the repository layer:the mockito will
     * automatically return the mocked object without calling to the actual repository layer method.
     *
     * # If we call directly from one layer to another layer it will break the unit testing
     * fundamentals(isolation of the unit) and that process need to be tested by integration test
     * */

    // CREATE ROOM
    @Test
    void createRoom_WithoutAmenities_Success() {
        Room room = new MockObjects().getAvailableRoom1();
        RoomDto roomDto = new MockObjects().getRoomDto();
        User user = new MockObjects().getUser1();
        RoomType roomType = new MockObjects().getRoomType1();

        when(userRepository.findById("111"))
                .thenReturn(Optional.of(user));
        when(roomTypeRepository.findById("2"))
                .thenReturn(Optional.of(roomType));
        when(roomRepository.save(any(Room.class)))
                .thenReturn(room);

        assertEquals(HttpStatus.OK, roomService.createRoom(roomDto).getStatusCode(),
                "Should have Status code '200 OK'");
        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    void createRoom_WhenUserNotFound_ThrowResourceNotFoundException() {
        Room room = new MockObjects().getAvailableRoom1();
        RoomDto roomDto = new MockObjects().getRoomDto();
        RoomType roomType = new MockObjects().getRoomType1();

        when(userRepository.findById("111"))
                .thenThrow(ResourceNotFoundException.class);
        when(roomTypeRepository.findById("2"))
                .thenReturn(Optional.of(roomType));
        when(roomRepository.save(any(Room.class)))
                .thenReturn(room);

        assertThrows(ResourceNotFoundException.class, () -> roomService.createRoom(roomDto),
                "Should throw ResourceNotFoundException");
        verify(roomTypeRepository, never()).findById("2");
        verify(roomRepository, never()).save(any(Room.class));
    }

    @Test
    void createRoom_WhenRoomTypeNotFound_ThrowResourceNotFoundException() {
        Room room = new MockObjects().getAvailableRoom1();
        RoomDto roomDto = new MockObjects().getRoomDto();
        User user = new MockObjects().getUser1();

        when(userRepository.findById("111"))
                .thenReturn(Optional.of(user));
        when(roomTypeRepository.findById("2"))
                .thenThrow(ResourceNotFoundException.class);
        when(roomRepository.save(any(Room.class)))
                .thenReturn(room);

        assertThrows(ResourceNotFoundException.class, () -> roomService.createRoom(roomDto),
                "Should throw ResourceNotFoundException");
        verify(roomTypeRepository, times(1)).findById("2");
        verify(roomRepository, never()).save(any(Room.class));
    }

    @Test
    void createRoom_WhenThereAreRoomAmenities_CreateRoomAmenities() {
        List<RoomAmenity> roomAmenities = new MockObjects().getRoomAmenities();
        Amenity amenity1 = new MockObjects().getAmenity1();
        Amenity amenity2 = new MockObjects().getAmenity2();

        Room room = new MockObjects().getRoomWithAmenities();
        RoomDto roomDto = new MockObjects().getRoomDtoWithAmenities();

        User user = new MockObjects().getUser1();
        RoomType roomType = new MockObjects().getRoomType1();

        // Mock Objects
        when(userRepository.findById("111"))
                .thenReturn(Optional.of(user));
        when(roomTypeRepository.findById("1"))
                .thenReturn(Optional.of(roomType));

        when(amenityRepository.findById("123"))
                .thenReturn(Optional.of(amenity1));
        when(amenityRepository.findById("456"))
                .thenReturn(Optional.of(amenity2));

//        for (String amenityId:
//                amenitiesIdList) {
//            when(amenityRepository.findById("123"))
//                    .thenReturn(Optional.of(amenity1));
//            when(amenityRepository.findById("456"))
//                    .thenReturn(Optional.of(amenity2));
//        }
        when(roomAmenityRepository.saveAll(any(ArrayList.class)))
                .thenReturn(roomAmenities);
        when(roomRepository.save(any(Room.class)))
                .thenReturn(room);

        assertEquals(HttpStatus.OK, roomService.createRoom(roomDto).getStatusCode(),
                "Should have Status code '200 OK'");
        verify(amenityRepository,times(2)).findById(any(String.class));
        verify(roomAmenityRepository, times(1)).saveAll(any(ArrayList.class));
    }

    @Test
    void createRoom_WhenAmenityNotFound_ThrowResourceNotFoundException() {
        RoomDto roomDto = new MockObjects().getRoomDtoWithAmenities();
        User user = new MockObjects().getUser1();
        RoomType roomType = new MockObjects().getRoomType1();
        Room room = new MockObjects().getRoomWithAmenities();
        Amenity amenity = new MockObjects().getAmenity1();
        List<RoomAmenity> roomAmenities = new MockObjects().getRoomAmenities();

        // Mock objects
        when(userRepository.findById("111"))
                .thenReturn(Optional.of(user));
        when(roomTypeRepository.findById("1"))
                .thenReturn(Optional.of(roomType));

        when(amenityRepository.findById("123"))
                .thenThrow(ResourceNotFoundException.class);
        when(amenityRepository.findById("456"))
                .thenReturn(Optional.of(amenity));

        when(roomAmenityRepository.saveAll(any(ArrayList.class)))
                .thenReturn(roomAmenities);
        when(roomRepository.save(any(Room.class)))
                .thenReturn(room);

        /*
        Code execution stops when the find of first amenity throws exception,
        Therefore amenityRepository calls only once
         */
        assertThrows(ResourceNotFoundException.class, () -> roomService.createRoom(roomDto),
                "Should throw ResourceNotFoundException");
        verify(amenityRepository, times(1)).findById(any(String.class));
        verify(roomRepository, never()).save(any(Room.class));
        verify(roomAmenityRepository, never()).saveAll(any(ArrayList.class));
    }

    // TODO: test the room availabilities when verify

    // / CREATE ROOM

    // GET ALL ROOMS
    @Test
    void getAllRooms_Success() {
        Room room1 = new MockObjects().getAvailableRoom1();
        Room room2 = new MockObjects().getAvailableRoom2();

        when(roomRepository.findAllByIsDeletedFalse())
                .thenReturn(Stream.of(room1, room2).collect(Collectors.toList()));

        assertEquals(HttpStatus.OK, roomService.getAllRooms().getStatusCode(),
                "Should have Status code '200 OK'");
        verify(roomRepository, times(1)).findAllByIsDeletedFalse();
    }

    @Test
    void getAllRooms_WhenNoRoomsFound_Success() {
        when(roomRepository.findAllByIsDeletedFalse())
                .thenReturn(Collections.emptyList());

        assertEquals(HttpStatus.OK, roomService.getAllRooms().getStatusCode(),
                "Should have Status code '200 OK'");
        verify(roomRepository, times(1)).findAllByIsDeletedFalse();
    }
    // / GET ALL ROOMS

    // GET ROOM BY ID
    @Test
    void getRoomById_Success() {
        Room room = new MockObjects().getAvailableRoom1();
        when(roomRepository.findByIdAndIsDeletedFalse(room.getId()))
                .thenReturn(Optional.of(room));

        assertEquals(HttpStatus.OK, roomService.getRoomById(room.getId()).getStatusCode(),
                "Should have Status code '200 OK'");
        verify(roomRepository, times(1)).findByIdAndIsDeletedFalse(room.getId());
    }

    @Test
    void getRoomById_WhenRoomNotFound_ThrowResourceNotFoundException() {
        when(roomRepository.findByIdAndIsDeletedFalse("111"))
                .thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> roomService.getRoomById("111"),
                "Should throw ResourceNotFoundException");
        verify(roomRepository, times(1)).findByIdAndIsDeletedFalse("111");
    }
    // / GET ROOM BY ID

    // GET ROOMS BY TYPE
    @Test
    void getRoomsByType_Success() {
        Room room1 = new MockObjects().getAvailableRoom1();
        Room room2 = new MockObjects().getAvailableRoom2();
        RoomType roomType = new MockObjects().getRoomType1();

        when(roomTypeRepository.findById("111"))
                .thenReturn(Optional.of(roomType));
        when(roomRepository.findAllByRoomTypeIdAndIsDeletedFalse("111"))
                .thenReturn(new ArrayList<>() {{ add(room1); add(room2); }});

        assertEquals(HttpStatus.OK, roomService.getRoomsByType("111").getStatusCode(),
                "Should have Status code '200 OK'");
        verify(roomRepository, times(1)).findAllByRoomTypeIdAndIsDeletedFalse("111");
    }

    @Test
    void getRoomsByType_WhenRoomTypeNotFound_ThrowResourceNotFoundException () {
        Room room1 = new MockObjects().getAvailableRoom1();
        Room room2 = new MockObjects().getAvailableRoom2();

        when(roomTypeRepository.findById("111"))
                .thenThrow(ResourceNotFoundException.class);
        when(roomRepository.findAllByRoomTypeIdAndIsDeletedFalse("111"))
                .thenReturn(new ArrayList<>() {{ add(room1); add(room2); }});

        assertThrows(ResourceNotFoundException.class, () -> roomService.getRoomsByType("111"),
                "Should throw ResourceNotFoundException");
        verify(roomRepository, never()).findAllByRoomTypeIdAndIsDeletedFalse("111");
    }

    @Test
    void getRoomsByType_WhenNoRoomsFound_Success() {
        RoomType roomType = new MockObjects().getRoomType1();

        when(roomTypeRepository.findById("111"))
                .thenReturn(Optional.of(roomType));
        when(roomRepository.findAllByRoomTypeIdAndIsDeletedFalse("111"))
                .thenReturn(Collections.emptyList());

        assertEquals(HttpStatus.OK, roomService.getRoomsByType("111").getStatusCode(),
                "Should have Status code '200 OK'");
        verify(roomRepository, times(1)).findAllByRoomTypeIdAndIsDeletedFalse("111");
    }
    // / GET ROOMS BY TYPE

    // UPDATE ROOM
    @Test
    void updateRoom_WithoutAmenities_Success() {
        RoomUpdateDto roomUpdateDto = new MockObjects().getRoomUpdateDto();
        Room room = new MockObjects().getAvailableRoom1();

        when(roomRepository.findByIdAndIsDeletedFalse("111"))
                .thenReturn(Optional.of(room));
        when(roomRepository.save(room))
                .thenReturn(room);

        assertEquals(HttpStatus.OK, roomService.updateRoom("111", roomUpdateDto).getStatusCode(),
                "Should have Status code '200 OK'");
        verify(roomRepository, times(1)).save(room);
    }

    @Test
    void updateRoom_WhenRoomNotFound_ThrowResourceNotFoundException() {
        RoomUpdateDto roomUpdateDto = new MockObjects().getRoomUpdateDto();
        when(roomRepository.findByIdAndIsDeletedFalse("111"))
                .thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> roomService.updateRoom("111", roomUpdateDto),
                "Should throw ResourceNotFoundException");
        verify(roomRepository, never()).save(any(Room.class));
    }

    // TODO: test update the room with amenities(and availabilities ?) when verify

    // / UPDATE ROOM

    // DELETE ROOM
    @Test
    void deleteRoom_Success() {
        Room room = new MockObjects().getAvailableRoom1();
        when(roomRepository.findByIdAndIsDeletedFalse("1"))
                .thenReturn(Optional.of(room));

        assertEquals(HttpStatus.OK, roomService.deleteRoom("1").getStatusCode(),
                "Should have Status code '200 OK'");
        verify(roomRepository, times(1)).deleteById("1");
    }

    @Test
    void deleteRoom_WhenRoomNotFound_ThrowResourceNotFoundException() {
        when(roomRepository.findByIdAndIsDeletedFalse("1"))
                .thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> roomService.deleteRoom("1"),
                "Should throw ResourceNotFoundException");
        verify(roomRepository, never()).deleteById("1");
    }
    // / DELETE ROOM

    // GET ROOMS BY USER
    @Test
    void getRoomsByUser_Success() {
        Room room1 = new MockObjects().getAvailableRoom1();
        Room room2 = new MockObjects().getAvailableRoom2();
        User user = new MockObjects().getUser1();

        when(userRepository.findById("111"))
                .thenReturn(Optional.of(user));
        when(roomRepository.findAllByUserIdAndIsDeletedFalse("111"))
                .thenReturn(Stream.of(room1, room2).collect(Collectors.toList()));

        assertEquals(HttpStatus.OK, roomService.getRoomsByUser("111").getStatusCode(),
                "Should have Status code '200 OK'");
        verify(roomRepository, times(1)).findAllByUserIdAndIsDeletedFalse("111");
    }

    @Test
    void getRoomsByUser_WhenUserNotFound_ThrowResourceNotFoundException() {
        Room room1 = new MockObjects().getAvailableRoom1();
        Room room2 = new MockObjects().getAvailableRoom2();


        when(userRepository.findById("111"))
                .thenThrow(ResourceNotFoundException.class);
        when(roomRepository.findAllByUserIdAndIsDeletedFalse("111"))
                .thenReturn(Stream.of(room1, room2).collect(Collectors.toList()));

        assertThrows(ResourceNotFoundException.class, () -> roomService.getRoomsByUser("111"),
                "Should throw ResourceNotFoundException");
        verify(roomRepository, never()).findAllByUserIdAndIsDeletedFalse("111");
    }

    @Test
    void getRoomsByUser_WhenNoRoomsFound_Success() {
        User user = new MockObjects().getUser1();

        when(userRepository.findById("111"))
                .thenReturn(Optional.of(user));
        when(roomRepository.findAllByUserIdAndIsDeletedFalse("111"))
                .thenReturn(Collections.emptyList());

        assertEquals(HttpStatus.OK, roomService.getRoomsByUser("111").getStatusCode(),
                "Should have Status code '200 OK'");
        verify(roomRepository, times(1)).findAllByUserIdAndIsDeletedFalse("111");

    }
    // / GET ROOMS BY USER

    // SEARCH ROOM
    @Test
    void searchRoom_Success() {
        Room room1 = new MockObjects().getAvailableRoom1();
        Room room2 = new MockObjects().getAvailableRoom2();

        when(roomRepository.searchRoom("Available"))
                .thenReturn(Stream.of(room1, room2).collect(Collectors.toList()));

        assertEquals(HttpStatus.OK, roomService.searchRoom("Available").getStatusCode(),
                "Should have Status code '200 OK'");
        verify(roomRepository, times(1)).searchRoom("Available");
    }

    @Test
    void searchRoom_WhenNoRoomsFound_Success() {
        when(roomRepository.searchRoom("New"))
                .thenReturn(Collections.emptyList());

        assertEquals(HttpStatus.OK, roomService.searchRoom("New").getStatusCode(),
                "Should have Status code '200 OK'");
        verify(roomRepository, times(1)).searchRoom("New");
    }
    // / SEARCH ROOM
}
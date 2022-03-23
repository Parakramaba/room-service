package com.hifigod.roomservice.service;

import com.hifigod.roomservice.exception.ResourceNotFoundException;
import com.hifigod.roomservice.model.Room;
import com.hifigod.roomservice.model.User;
import com.hifigod.roomservice.repository.AmenityRepository;
import com.hifigod.roomservice.repository.RoomRepository;
import com.hifigod.roomservice.repository.UserRepository;
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
class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private AmenityRepository amenityRepository;

    @MockBean
    private UserRepository userRepository;

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
    @Test
    void createRoom() {
    }

    @Test
    void getAllRooms_Success() {
        Room room1 = new MockObjects().getAvailableRoom1();
        Room room2 = new MockObjects().getAvailableRoom2();

        when(roomRepository.findAllByDeletedFalse())
                .thenReturn(Stream.of(room1, room2).collect(Collectors.toList()));

        assertEquals(HttpStatus.OK, roomService.getAllRooms().getStatusCode(),
                "Should have Status code '200 OK'");
        verify(roomRepository, times(1)).findAllByDeletedFalse();
    }

    @Test
    void getAllRooms_ThrowResourceNotFoundException_WhenNoRoomsFound() {
        when(roomRepository.findAllByDeletedFalse())
                .thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> roomService.getAllRooms(),
                "Should throw ResourceNotFoundException");
        verify(roomRepository, times(1)).findAllByDeletedFalse();
    }

    @Test
    void getRoomById_Success() {
        Room room = new MockObjects().getAvailableRoom1();
        when(roomRepository.findByIdAndDeletedFalse(room.getId()))
                .thenReturn(Optional.of(room));

//        Response response = (Response) roomService.getRoomById(room.getId()).getBody();
//        Room actualRoom = (Room) response.getData();
//        assertEquals(room, actualRoom);
        assertEquals(HttpStatus.OK, roomService.getRoomById(room.getId()).getStatusCode(),
                "Should have Status code '200 OK'");
        verify(roomRepository, times(1)).findByIdAndDeletedFalse(room.getId());
    }

    @Test
    void getRoomById_ThrowResourceNotFoundException_WhenRoomNotFound() {
        when(roomRepository.findByIdAndDeletedFalse("111"))
                .thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> roomService.getRoomById("111"),
                "Should throw ResourceNotFoundException");
        verify(roomRepository, times(1)).findByIdAndDeletedFalse("111");
    }

    @Test
    void updateRoom() {
    }

    @Test
    void deleteRoom() {
    }

    @Test
    void getRoomsByUser_Success() {
        Room room1 = new MockObjects().getAvailableRoom1();
        Room room2 = new MockObjects().getAvailableRoom2();
        User user = new MockObjects().getUser1();

        when(userRepository.findById("111"))
                .thenReturn(Optional.of(user));
        when(roomRepository.findAllByUserIdAndDeletedFalse("111"))
                .thenReturn(Stream.of(room1, room2).collect(Collectors.toList()));

//        assertEquals(new ArrayList<>(){{ add(room1); add(room2);}},
//                roomService.getRoomsByUser("111").getBody());
        assertEquals(HttpStatus.OK, roomService.getRoomsByUser("111").getStatusCode(),
                "Should have Status code '200 OK'");
        verify(roomRepository, times(1)).findAllByUserIdAndDeletedFalse("111");
    }

    @Test
    void getRoomsByUser_ThrowResourceNotFoundException_WhenNoUserFound() {
        Room room1 = new MockObjects().getAvailableRoom1();
        Room room2 = new MockObjects().getAvailableRoom2();


        when(userRepository.findById("111"))
                .thenThrow(ResourceNotFoundException.class);
        when(roomRepository.findAllByUserIdAndDeletedFalse("111"))
                .thenReturn(Stream.of(room1, room2).collect(Collectors.toList()));

        assertThrows(ResourceNotFoundException.class, () -> roomService.getRoomsByUser("111"),
                "Should throw ResourceNotFoundException");
        verify(roomRepository, never()).findAllByUserIdAndDeletedFalse("111");
    }

    @Test
    void getRoomsByUser_ThrowResourceNotFoundException_WhenNoRoomsFound() {
        User user = new MockObjects().getUser1();

        when(userRepository.findById("111"))
                .thenReturn(Optional.of(user));
        when(roomRepository.findAllByUserIdAndDeletedFalse("111"))
                .thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> roomService.getRoomsByUser("111"),
                "Should throw ResourceNotFoundException");
        verify(roomRepository, times(1)).findAllByUserIdAndDeletedFalse("111");

    }

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
    void searchRoom_ThrowResourceNotFoundException_WhenNoRoomsFound() {
        when(roomRepository.searchRoom("New"))
                .thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> roomService.searchRoom("New"),
                "Should throw ResourceNotFoundException");
        verify(roomRepository, times(1)).searchRoom("New");
    }
}
package com.hifigod.roomservice.service;

import com.hifigod.roomservice.exception.ResourceNotFoundException;
import com.hifigod.roomservice.model.Amenity;
import com.hifigod.roomservice.repository.AmenityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class)
//@ExtendWith(SpringExtension.class)
@SpringBootTest
class AmenityServiceTest {

    @Autowired
    private AmenityService amenityService;

    @MockBean
    private AmenityRepository amenityRepository;

    @Test
    void getAllAmenities_Success() {
        Amenity amenity1 = new MockObjects().getAmenity1();
        Amenity amenity2 = new MockObjects().getAmenity2();

        when(amenityRepository.findAll())
                .thenReturn(Stream.of(amenity1, amenity2).collect(Collectors.toList()));

        assertEquals(HttpStatus.OK, amenityService.getAllAmenities().getStatusCode(),
                "Should have Status code '200 OK'");
        verify(amenityRepository, times(1)).findAll();
    }

    @Test()
    void getAllAmenities_WhenNoAmenitiesFound_ThrowResourceNotFoundException() {
        when(amenityRepository.findAll())
                .thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class,()-> amenityService.getAllAmenities(),
                "Should throw ResourceNotFoundException");
        verify(amenityRepository, times(1)).findAll();
    }
}
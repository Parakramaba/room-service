package com.hifigod.roomservice.service;

import com.hifigod.roomservice.exception.ResourceNotFoundException;
import com.hifigod.roomservice.model.Amenity;
import com.hifigod.roomservice.repository.AmenityRepository;
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

//@ExtendWith(MockitoExtension.class)
//@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AmenityServiceTest {

    @Autowired
    private AmenityService amenityService;

    @MockBean
    private AmenityRepository amenityRepository;

    @Test
    public void getAllAmenities_Success() {
        Amenity amenity1 = new MockObjects().getAmenity1();
        Amenity amenity2 = new MockObjects().getAmenity2();
        when(amenityRepository.findAll()).thenReturn(Stream
                .of(amenity1, amenity2).collect(Collectors.toList()));
        assertEquals(HttpStatus.OK, amenityService.getAllAmenities().getStatusCode(),
                "Should return '200 OK'");
//        verify(amenityRepository, times(2)).findAll();
    }

    @Test()
    public void getAllAmenities_ThrowResourceNotFoundException_WhenNoAmenitiesFound() {
        when(amenityRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(ResourceNotFoundException.class,()-> amenityService.getAllAmenities(),
                "Should throw ResourceNotFoundException");
        verify(amenityRepository, times(1)).findAll();
    }
}
package com.hifigod.roomservice.controller;

import com.hifigod.roomservice.exception.ResourceNotFoundException;
import com.hifigod.roomservice.service.AdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rooms/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "Return all deleted rooms")
    @GetMapping("/deleted-rooms")
    public ResponseEntity<?> getAllDeletedRooms() throws ResourceNotFoundException {
        return adminService.getAllDeletedRooms();
    }
}

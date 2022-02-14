package com.hifigod.roomservice.repository;

import com.hifigod.roomservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}

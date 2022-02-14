package com.hifigod.roomservice.repository;

import com.hifigod.roomservice.model.RoomDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomDocumentRepository extends JpaRepository<RoomDocument, String> {
}

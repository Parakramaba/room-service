package com.hifigod.roomservice.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "roomAvailability")
@Data
public class RoomAvailability implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "roomId", referencedColumnName = "id")
    private Room room;

    private LocalDate day;

    private String session;

    private LocalTime startTime;

    private LocalTime endTime;
}

package com.hifigod.roomservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "amenity")
@Data
public class Amenity implements Serializable {

    @Id
    @Column(nullable = false)
    private String id;

    @Column(nullable = false, length = 254)
    private String name;

//    @ManyToMany(mappedBy = "amenities")
////    @JsonIgnoreProperties("amenities")
//    @JsonIgnore
//    private List<Room> rooms;

    @OneToMany(mappedBy = "amenity")
    @JsonIgnore
    private List<RoomAmenity> roomAmenities;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    //    @UpdateTimestamp
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime updatedAt;

}

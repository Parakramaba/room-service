package com.hifigod.roomservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "roomAmenity")
@SQLDelete(sql = "UPDATE room_amenity SET deleted = true WHERE id=?")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RoomAmenity implements Serializable {

    @Id
    @Column(nullable = false)
    private String id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "roomId", referencedColumnName = "id")
    @JsonIgnoreProperties("roomAmenities")
    private Room room;

    @ManyToOne(optional = false)
    @JoinColumn(name = "amenityId", referencedColumnName = "id")
    @JsonIgnoreProperties("roomAmenities")
    private Amenity amenity;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

//    @UpdateTimestamp
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime updatedAt;

    @JsonIgnore
    private boolean deleted = Boolean.FALSE;
}

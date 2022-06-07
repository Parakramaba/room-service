package com.hifigod.roomservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Generated;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "roomAmenity")
@SQLDelete(sql = "UPDATE room_amenity SET is_deleted = true WHERE id=?")
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

    @Generated(value = GenerationTime.INSERT)
    @Column(columnDefinition = "boolean default false")
    @JsonIgnore
    private boolean isDeleted = Boolean.FALSE;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

//    @UpdateTimestamp
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime updatedAt;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonIgnore
//    private LocalDateTime deletedAt;
}

package com.hifigod.roomservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.*;
//import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
//import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
//@Indexed(index = "full_text_idx")
@Table(name = "room",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "userId"}))
@Data
public class Room implements Serializable {

    @Id
    @Column(nullable = false)
    private String id;

//    @FullTextField
    @Column(nullable = false, length = 254)
    private String name;

//    @FullTextField
    @Column(columnDefinition = "text")
    private String description;

    // TODO : Resolve recursive data calls

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "FieldHandler"})
    @JsonBackReference
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "roomTypeId", referencedColumnName = "id")
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "FieldHandler"})
    @JsonBackReference
    private RoomType roomType;

    @Column(nullable = false)
    private int noOfGuest;

    @Column(nullable = false)
    private int hourlyRate;

    @Column(columnDefinition = "decimal(8,6)")
    private Double latitude;

    @Column(columnDefinition = "decimal(9,6)")
    private Double longitude;

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "countryId", referencedColumnName = "id")
//    private Country country;

//    @FullTextField
    @Column(nullable = false, length = 254)
    private String country;

    @Column(nullable = false)
    private String streetAddress;

    @Column(nullable = false)
    private String apartmentNo;

//    @FullTextField
    @Column(nullable = false, length = 254)
    private String city;

    @Column(nullable = false, length = 10)
    private String postCode;

    private long setupCost;

    @Generated(value = GenerationTime.ALWAYS)
    @Column(columnDefinition = "varchar(50) default 'Not Verified'")
    private String verifyStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime verifiedOn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

//    @UpdateTimestamp
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(
            name = "roomAmenity",
            joinColumns = {@JoinColumn(name = "roomId", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "amenityId", referencedColumnName = "id")}
    )
    @JsonIgnore
    private List<Amenity> amenities;

//    @OneToMany(mappedBy = "room")
//    private List<RoomDocument> roomImages;
//
//    @OneToOne(mappedBy = "room")
//    private SetupInformation setupInformation;

    @OneToMany(mappedBy = "room")
    @JsonIgnore
    private List<RoomAvailability> roomAvailabilities;

}

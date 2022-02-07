package com.hifigod.roomservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "room")
@Data
public class Room implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "roomTypeId", referencedColumnName = "id")
    private RoomType roomType;

    @Column(nullable = false)
    private int noOfGuest;

    @Column(nullable = false)
    private int hourlyRate;

    private Double latitude;

    private Double longitude;

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "countryId", referencedColumnName = "id")
//    private Country country;

    @Column(nullable = false)
    private String streetAddress;

    @Column(nullable = false)
    private String apartmentNo;

    @Column(nullable = false, length = 254)
    private String city;

    @Column(nullable = false, length = 10)
    private String postCode;

    private long setupCost;

    @Column(columnDefinition = "varchar(50) default 'Not Verified'")
    private String verifyStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime verifiedOn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime publishedOn;

    @OneToMany(mappedBy = "room")
    private List<RoomDocument> roomImages;

    @OneToOne(mappedBy = "room")
    private SetupInformation setupInformation;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<RoomAvailability> roomAvailabilities;

}

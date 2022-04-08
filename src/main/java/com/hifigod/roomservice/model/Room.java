package com.hifigod.roomservice.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Generated;
//import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
//import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
//@Indexed(index = "full_text_idx")
@Table(name = "room",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "userId"}))
@SQLDelete(sql = "UPDATE room SET deleted = true WHERE id=?")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Room implements Serializable {

    // TODO : Validation handling

    @Id
    @Column(nullable = false)
    private String id;

//    @FullTextField
    @Column(nullable = false, length = 254)
    private String name;

//    @FullTextField
    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "userId", referencedColumnName = "id")
//    @JsonManagedReference
    @JsonIgnoreProperties("rooms")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "roomTypeId", referencedColumnName = "id")
//    @JsonManagedReference
    @JsonIgnoreProperties("rooms")
    private RoomType roomType;

    @Column(nullable = false)
    private Integer noOfGuest;

    @Column(nullable = false)
    private Integer hourlyRate;

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

    private Long setupCost;

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

//    @JsonIgnore
    private boolean deleted = Boolean.FALSE;

//    @ManyToMany
//    @JoinTable(
//            name = "roomAmenity",
//            joinColumns = {@JoinColumn(name = "roomId", referencedColumnName = "id")},
//            inverseJoinColumns = {@JoinColumn(name = "amenityId", referencedColumnName = "id")}
//    )
////    @JsonIgnore
//    @JsonIgnoreProperties("rooms")
//    private List<Amenity> amenities;

    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("room")
    private List<RoomAmenity> roomAmenities;

//    @OneToMany(mappedBy = "room")
//    @JsonIgnoreProperties("room")
//    private List<RoomDocument> roomDocuments;

//    @OneToOne(mappedBy = "room")
//    @JsonIgnoreProperties("room")
//    private SetupInformation setupInformation;

    // TODO: find the best practice to do the cascade deletion

    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
//    @JsonManagedReference
    @JsonIgnoreProperties("room")
    private List<RoomAvailability> roomAvailabilities;

}

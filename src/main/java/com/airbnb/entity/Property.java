package com.airbnb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "number_of_guest", nullable = false)
    private Integer numberOfGuest;

    @Column(name = "number_of_beds", nullable = false)
    private Integer numberOfBeds;

    @Column(name = "number_of_bathrooms", nullable = false)
    private Integer numberOfBathrooms;

    @Column(name = "number_of_bedrooms", nullable = false)
    private String numberOfBedrooms;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

}
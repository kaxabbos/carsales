package com.carsales.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Dealerships {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String name;
    private String address;
    private String poster;
    @Column(length = 5000)
    private String description;
    @OneToMany(mappedBy = "dealership", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Cars> cars;
    @ManyToOne(fetch = FetchType.LAZY)
    private Users owner;

    public Dealerships(String name,String address,  String description, String poster) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.poster = poster;
    }

    public void addCar(Cars car) {
        this.cars.add(car);
        car.setDealership(this);
    }

    public void removeCar(Cars car) {
        this.cars.remove(car);
        car.setDealership(null);
    }
}

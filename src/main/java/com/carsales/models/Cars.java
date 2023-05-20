package com.carsales.models;

import com.carsales.models.enums.Brand;
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
public class Cars {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String name;
    private String origin;
    @Column(length = 5000)
    private String description;
    private String date;
    private String poster;
    private String[] screenshots;
    private int year;
    private int count;
    @Enumerated(EnumType.STRING)
    private Brand brand;
    @ManyToOne(fetch = FetchType.LAZY)
    private Dealerships dealership;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Income income;
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comments> comments;

    public Cars(String name, String origin, String description, String date, String poster, String[] screenshots, int year, int price, int count, Brand brand) {
        this.name = name;
        this.origin = origin;
        this.description = description;
        this.date = date;
        this.poster = poster;
        this.screenshots = screenshots;
        this.year = year;
        this.count = count;
        this.brand = brand;
        this.income = new Income(price);
    }

    public void addComment(Comments comment) {
        comments.add(comment);
        comment.setBook(this);
    }

    public void removeComment(Comments comment) {
        comments.remove(comment);
        comment.setBook(null);
    }
}

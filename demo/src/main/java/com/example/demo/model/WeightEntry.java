package com.example.demo.model;

import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="WeightEntry")
public class WeightEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne()
    private User user;

    @OneToMany(mappedBy = "weightEntry")
    private Set<Image> imageSet = new HashSet<>();

    private double weight;
    private LocalDate date;

    public WeightEntry() {
    }

    public WeightEntry(double weight, LocalDate date, User user) {
        this.weight = weight;
        this.date = date;
        this.user = user;
    }

    public WeightEntry(User user, Set<Image> imageSet, double weight, LocalDate date) {
        this.user = user;
        this.imageSet = imageSet;
        this.weight = weight;
        this.date = date;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<Image> getImageSet() {
        return imageSet;
    }

    public void setImageSet(Set<Image> imageSet) {
        this.imageSet = imageSet;
    }

    @Override
    public String toString() {
        return "{ weight=" + weight +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeightEntry that = (WeightEntry) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

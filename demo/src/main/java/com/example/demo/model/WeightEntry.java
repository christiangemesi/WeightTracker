package com.example.demo.model;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name="WeightEntry")
public class WeightEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne()
    private User user;

    @OneToMany(mappedBy = "weightEntry")
    private List<Image> imageSet = new ArrayList<>();

    private double weight;
    private Date date;

    public WeightEntry() {
    }

    public WeightEntry(double weight, Date date, User user) {
        this.weight = weight;
        this.date = date;
        this.user = user;
    }

    public WeightEntry(User user, List<Image> imageSet, double weight, Date date) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Image> getImageList() {
        return imageSet;
    }

    public void setImageList(List<Image> imageSet) {
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

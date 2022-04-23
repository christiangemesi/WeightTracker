package com.example.demo.model;

import javax.persistence.*;
import java.util.Date;


@Entity
public class WeightEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    //TODO I have no clue what JoinTable does, but it works without it aswell
    @ManyToOne
    //@JoinTable(name = "weightEntry_user",joinColumns = @JoinColumn(name = "weightEntry_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private User user;

    private double weight;
    private Date date;


    public WeightEntry(double weight, Date date, User user) {
        this.weight = weight;
        this.date = date;
        this.user = user;
    }

    public WeightEntry() {
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

    @Override
    public String toString() {
        return "WeightEntry{" +
                "id=" + id +
                ", user=" + user +
                ", weight=" + weight +
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

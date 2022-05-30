package ch.fhnw.webeng.weighttracker.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name="WeightEntry")
public class WeightEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne()
    private User user;

    @OneToMany(mappedBy = "weightEntry", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    private double weight;

    @Column(nullable = false)
    private LocalDate date;

    public WeightEntry() {
    }

    public WeightEntry(double weight, LocalDate date, User user) {
        this.weight = weight;
        this.date = date;
        this.user = user;
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

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "WeightEntry{" +
            "id=" + id +
            ", user=" + user +
            ", images=" + images +
            ", weight=" + weight +
            ", date=" + date +
            '}';
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        var that = (WeightEntry) other;
        return Objects.equals(id, that.id)
            && Objects.equals(weight, that.weight)
            && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

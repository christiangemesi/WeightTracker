package com.example.demo.model;

import javax.persistence.*;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private WeightEntry weightEntry;

    public Image() {
    }

    public Image(WeightEntry weightEntry) {
        this.weightEntry = weightEntry;
    }


    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", weightEntry=" + weightEntry +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        return id != null ? id.equals(image.id) : image.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WeightEntry getWeightEntry() {
        return weightEntry;
    }

    public void setWeightEntry(WeightEntry weightEntry) {
        this.weightEntry = weightEntry;
    }
}

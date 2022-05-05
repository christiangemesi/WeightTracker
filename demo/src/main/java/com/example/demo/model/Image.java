package com.example.demo.model;

import javax.persistence.*;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private WeightEntry weightEntry;

    private String name;

    @Lob
    private byte[] file;

    public Image() {
    }

    public Image(WeightEntry weightEntry) {
        this.weightEntry = weightEntry;
    }

    public Image(String name, byte[] file, WeightEntry weightEntry) {
        this.name = name;
        this.file = file;
        this.weightEntry = weightEntry;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", weightEntry=" + weightEntry +
                ", name='" + name + '\'' +
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WeightEntry getWeightEntry() {
        return weightEntry;
    }

    public void setWeightEntry(WeightEntry weightEntry) {
        this.weightEntry = weightEntry;
    }
}

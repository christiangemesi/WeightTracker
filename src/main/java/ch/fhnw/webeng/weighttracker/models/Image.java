package ch.fhnw.webeng.weighttracker.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @JsonIgnore
    private WeightEntry weightEntry;

    private String name;

    @Lob
    @JsonIgnore
    private byte[] file;

    private String mimeType;

    public Image() {}

    public Image(String name, byte[] file, WeightEntry weightEntry, String mimeType) {
        this.name = name;
        this.file = file;
        this.weightEntry = weightEntry;
        this.mimeType = mimeType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
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

    @Override
    public String toString() {
        return "Image{" +
            "id=" + id +
            ", weightEntry=" + weightEntry +
            ", name='" + name + '\'' +
            ", mimeType='" + mimeType + '\'' +
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
        var that = (Image) other;
        return Objects.equals(id, that.id)
            && Objects.equals(name, that.name)
            && Objects.equals(mimeType, that.mimeType)
            && Objects.equals(weightEntry, that.weightEntry);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
package com.example.demo.model;

import java.sql.Date;
import java.util.HashMap;
import java.util.Set;

public class User {
    private String email;
    private Set<WeightEntries> weightEntriesSet;

    public User(String email, Set<WeightEntries> weightEntriesSet) {
        this.email = email;
        this.weightEntriesSet = weightEntriesSet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<WeightEntries> getWeightEntriesSet() {
        return weightEntriesSet;
    }

    public void setWeightEntriesSet(Set<WeightEntries> weightEntriesSet) {
        this.weightEntriesSet = weightEntriesSet;
    }


}

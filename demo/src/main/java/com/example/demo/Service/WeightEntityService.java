package com.example.demo.Service;

import com.example.demo.model.User;
import com.example.demo.model.WeightEntry;
import com.example.demo.repositories.WeightEntryRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
public class WeightEntityService {
    private final WeightEntryRepository weightEntryRepository;

    public WeightEntityService(WeightEntryRepository weightEntryRepository) {
        this.weightEntryRepository = weightEntryRepository;
    }

    public void addWeightEntity(double weight, Date date, User user) {
        this.weightEntryRepository.save(new WeightEntry(weight,date, user));
    }

    public Optional<WeightEntry>  getWeightEntryRepository(User user) {
        weightEntryRepository.findById(user);
    }
}

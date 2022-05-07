package com.example.demo.Service;

import com.example.demo.model.User;
import com.example.demo.model.WeightEntry;
import com.example.demo.repositories.WeightEntryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WeightEntityService {

    private final WeightEntryRepository weightEntryRepository;

    public WeightEntityService(WeightEntryRepository weightEntryRepository) {
        this.weightEntryRepository = weightEntryRepository;
    }

    public WeightEntry addWeightEntity(double weight, LocalDate date, User user) {
        return this.weightEntryRepository.save(new WeightEntry(weight, date, user));
    }


    public WeightEntry isDuplicateWeightEntryPresent(LocalDate date, User user) {
        return weightEntryRepository.listWithDuplicates(user.getId(), date);
    }

    public void removeWeightEntryById(Long weightEntryId) {
        weightEntryRepository.deleteById(weightEntryId);
    }

    public List<WeightEntry> getAllWeights(Long userId) {
        return weightEntryRepository.findAllByUserId(userId);
    }
}

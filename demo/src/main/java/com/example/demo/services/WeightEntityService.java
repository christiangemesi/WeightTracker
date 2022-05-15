package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.models.WeightEntry;
import com.example.demo.repositories.WeightEntryRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class WeightEntityService {

    private final WeightEntryRepository weightEntryRepository;

    public WeightEntityService(WeightEntryRepository weightEntryRepository) {
        this.weightEntryRepository = weightEntryRepository;
    }

    public WeightEntry addWeightEntity(double weight, Date date, User user) {
        return this.weightEntryRepository.save(new WeightEntry(weight, date, user));
    }

    public WeightEntry updateWeightEntry(long id, double weight, Date date) {
        WeightEntry weightEntry = getWeightEntryById(id);
        weightEntry.setWeight(weight);
        weightEntry.setDate(date);

        return weightEntryRepository.save(weightEntry);
    }

    public WeightEntry isDuplicateWeightEntryPresent(Date date, User user) {
        return weightEntryRepository.listWithDuplicates(user.getId(), date);
    }

    public void removeWeightEntryById(Long weightEntryId) {
        weightEntryRepository.deleteById(weightEntryId);
    }

    public List<WeightEntry> getAllWeights(Long userId) {
        return weightEntryRepository.findAllByUserId(userId).stream()
                .sorted(Comparator.comparing(WeightEntry::getDate))
                .toList();
    }

    public WeightEntry getWeightEntryById(long weightEntryId) {
        return weightEntryRepository
                .findById(weightEntryId)
                .orElseThrow();
    }
}

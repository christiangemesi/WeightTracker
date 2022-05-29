package ch.fhnw.webeng.weighttracker.services;

import ch.fhnw.webeng.weighttracker.models.User;
import ch.fhnw.webeng.weighttracker.models.WeightEntry;
import ch.fhnw.webeng.weighttracker.repositories.WeightEntryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class WeightEntityService {

    private final WeightEntryRepository weightEntryRepository;

    public WeightEntityService(WeightEntryRepository weightEntryRepository) {
        this.weightEntryRepository = weightEntryRepository;
    }

    public WeightEntry save(WeightEntry entry) {
        WeightEntry duplicateEntry = isDuplicateWeightEntryPresent(entry.getDate(), entry.getUser());
        if (duplicateEntry != null) {
            removeWeightEntryById(duplicateEntry.getId());
        }
        return this.weightEntryRepository.save(entry);
    }

    public WeightEntry isDuplicateWeightEntryPresent(LocalDate date, User user) {
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

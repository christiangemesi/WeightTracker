package ch.fhnw.webeng.weighttracker.services;

import ch.fhnw.webeng.weighttracker.models.WeightEntry;
import ch.fhnw.webeng.weighttracker.repositories.WeightEntryRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeightEntityService {

    private final WeightEntryRepository weightEntryRepository;

    public WeightEntityService(WeightEntryRepository weightEntryRepository) {
        this.weightEntryRepository = weightEntryRepository;
    }

    public WeightEntry find(long weightEntryId) {
        return weightEntryRepository
            .findById(weightEntryId)
            .orElseThrow();
    }

    public List<WeightEntry> findAllByUserId(Long userId) {
        return weightEntryRepository.findAllByUserId(userId, Sort.by(Sort.Direction.ASC, "date"));
    }

    public WeightEntry save(WeightEntry entry) {
        weightEntryRepository.findDuplicate(entry.getUser().getId(), entry.getDate()).ifPresent((duplicate) -> {
            entry.setId(duplicate.getId());
        });
        return this.weightEntryRepository.save(entry);
    }

    public void removeWeightEntryById(Long weightEntryId) {
        weightEntryRepository.deleteById(weightEntryId);
    }
}

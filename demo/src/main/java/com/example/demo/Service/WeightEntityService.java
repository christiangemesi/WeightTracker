package com.example.demo.Service;

import com.example.demo.model.User;
import com.example.demo.model.WeightEntry;
import com.example.demo.repositories.WeightEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class WeightEntityService {

    private final WeightEntryRepository weightEntryRepository;

    public WeightEntityService(WeightEntryRepository weightEntryRepository) {
        this.weightEntryRepository = weightEntryRepository;
    }

    public WeightEntry addWeightEntity(double weight, Date date, User user) {
        return this.weightEntryRepository.save(new WeightEntry(weight,date, user));
    }


    public boolean isDuplicateWeightEntryPresent(Date date,User user){
        return weightEntryRepository.listWithDuplicates(user.getId(),date).size()>=1;
    }

    public void removeWeightEntry(WeightEntry weightEntry){
        weightEntryRepository.deleteById(weightEntry.getId());
    }

    public List<WeightEntry> getAllWeights(Long userId) {
        return weightEntryRepository.findAllByUserId(userId);
    }
}

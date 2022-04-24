package com.example.demo.repositories;

import com.example.demo.model.WeightEntry;
import org.springframework.data.repository.CrudRepository;

public interface WeightEntryRepository extends CrudRepository<WeightEntry,Long> {
}

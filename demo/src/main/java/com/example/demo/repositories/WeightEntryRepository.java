package com.example.demo.repositories;

import com.example.demo.model.WeightEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeightEntryRepository extends JpaRepository<WeightEntry,Long> {
}

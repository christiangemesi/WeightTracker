package com.example.demo.repositories;

import com.example.demo.model.WeightEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeightEntryRepository extends JpaRepository<WeightEntry,Long> {


    @Query("SELECT weightEntry.weight"
            + " FROM "
            + "WeightEntry weightEntry"
            + " WHERE "
            + "weightEntry.id = :#{#userId}")
    Optional<WeightEntry> findById(@Param("userid") Long userId);


}

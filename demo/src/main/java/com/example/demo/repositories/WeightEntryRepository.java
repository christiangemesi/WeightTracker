package com.example.demo.repositories;

import com.example.demo.model.User;
import com.example.demo.model.WeightEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeightEntryRepository extends JpaRepository<WeightEntry,Long> {

    @Query(
        "SELECT WeightEntry.weight"
            + " FROM "
            + "WeightEntry weightEntry"
            + " WHERE "
            + "weightEntry.user.id = :#{#user.id}"
    )
    Optional<WeightEntry> findWeightEntryBy(@Param("user") User user);



}

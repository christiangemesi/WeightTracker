package com.example.demo.repositories;

import com.example.demo.model.WeightEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeightEntryRepository extends JpaRepository<WeightEntry,Long> {


    @Query("SELECT weightEntry"
            + " FROM "
            + "WeightEntry weightEntry"
            + " WHERE "
            + "weightEntry.user.id = :#{#userId}")
    List<WeightEntry> findWeightEntryById(@Param("userId") Long userId);

    List<WeightEntry> findAllByUserId(Long userId);


}

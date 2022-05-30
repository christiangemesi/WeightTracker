package ch.fhnw.webeng.weighttracker.repositories;

import ch.fhnw.webeng.weighttracker.models.WeightEntry;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeightEntryRepository extends JpaRepository<WeightEntry,Long> {
    List<WeightEntry> findAllByUserId(Long userId, Sort sort);

    @Query("SELECT weightEntry"
            + " FROM "
            + "WeightEntry weightEntry"
            + " WHERE "
            + "weightEntry.user.id = :userId"
            + " AND "
            + "weightEntry.date = :date")
    Optional<WeightEntry> findDuplicate(@Param("userId") Long userId, @Param("date") LocalDate date);
}
